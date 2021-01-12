package pl.sztyro.main.services;

import com.google.gson.Gson;
import org.hibernate.Session;
import org.jasypt.util.text.AES256TextEncryptor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sztyro.main.config.HibernateConf;
import pl.sztyro.main.model.Company;
import pl.sztyro.main.model.Database;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseService {

    private static final Logger _logger = LoggerFactory.getLogger(DatabaseService.class);

    @Autowired
    HibernateConf conf;

    public String decodePassword(String password) {
        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword("dev-env-secret");
        return textEncryptor.decrypt(password);
    }

    public Connection prepareConnection(Database database) throws SQLException {

        return DriverManager.getConnection(
                "jdbc:mysql://" + database.getUrl()
                        + ":" + database.getPort()
                        + "/" + database.getDatabase()
                        + "?user=" + database.getLogin()
                        + (database.getPassword() != null ? ("&password=" + decodePassword(database.getPassword())) : "")

        );

    }

    public Object getTables(Database database) throws SQLException {
        _logger.info("Pobieranie tabel bazy: " + database.getDatabase());

        Connection connection = prepareConnection(database);

        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = \'" + database.getDatabase() + "\';";

        Statement statement = connection.createStatement();

        ResultSet result = statement.executeQuery(sql);

        JSONArray array = new JSONArray();
        while (result.next()) {
            array.put(new JSONObject().put("name", result.getString(1)));

        }
        connection.close();
        return array.toString();
    }

    public Object getTableDetails(Database database, String tableName) throws SQLException {
        _logger.info("Pobieranie szczegółów tabeli: " + tableName);

        Connection connection = prepareConnection(database);
        //String sql = "SELECT * FROM all_tables where owner = (select user from dual)";
        String sql = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = \'" + database.getDatabase() + "\' AND TABLE_NAME = \'" + tableName + "\';";
        Statement statement = connection.createStatement();

        ResultSet result = statement.executeQuery(sql);

        JSONArray array = new JSONArray();


        while (result.next()) {

            JSONObject table = new JSONObject();
            table.put("name", result.getString("COLUMN_NAME"));
            table.put("nullable", result.getString("IS_NULLABLE").equals("YES"));
            table.put("dataType", result.getString("DATA_TYPE"));
            table.put("autoIncrement", result.getString("EXTRA").contains("auto_increment"));

            array.put(table);
        }
        connection.close();

        System.out.println(array.toString(2));
        return array.toString();
    }

    public Object getTableContent(Database database, String tableName) throws SQLException {
        _logger.info("Pobieranie zawartości tabeli: " + tableName);

        Connection connection = prepareConnection(database);
        //String sql = "SELECT * FROM all_tables where owner = (select user from dual)";
        String sql = "SELECT * FROM " + tableName + ";";
        Statement statement = connection.createStatement();

        ResultSet result = statement.executeQuery(sql);

        JSONArray array = new JSONArray();

        ResultSetMetaData metaData = result.getMetaData();
        int count = metaData.getColumnCount();

        ArrayList<String> columnNames = new ArrayList<>();

        for (int i = 1; i <= count; i++)
            columnNames.add(metaData.getColumnName(i));

        while (result.next()) {


            JSONObject table = new JSONObject();

            columnNames.forEach(name -> {
                try {
                    table.put(name, result.getObject(name));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            array.put(table);
        }
        connection.close();

        System.out.println(array.toString(2));
        return array.toString();
    }

    public void insertRow(Database database, String tableName, JSONArray body) throws SQLException, ParseException {
        _logger.info("Dodawanie rekordu do tabeli: " + tableName);

        Connection connection = prepareConnection(database);

        String values = "";
        String columns = "";

        List<Object> list = body.toList();
        for (Object elem : list) {
            JSONObject object = new JSONObject(new Gson().toJson(elem));
            object.getString("name");
            String dataType = object.getString("dataType");
            switch (dataType) {
                case "int":
                    values += nullIfNotFound(object) + ",";
                    columns += object.has("name") ? object.getString("name") + "," : "null,";
                    ;
                    break;
                case "varchar":

                case "text":
                    values += object.has("value") ? "'" + object.getString("value") + "'," : "null,";
                    columns += object.getString("name") + ",";
                    break;
                case "date":
                    values += object.has("value") ? "'" + new Date(object.getLong("value")) + "'," : "null,";
                    columns += object.getString("name") + ",";
                    break;
//                case "date":
//                    values += "'" + nullIfNotFound(object) + "',";
//                    columns += "'" + nullIfNotFound(object) + "',";
//                    break;
//                case "text":
//                    values += "'" + nullIfNotFound(object) + "',";
//                    columns += "'" + nullIfNotFound(object) + "',";
//                    break;
//                default:
//                    values += "null,";
//                    break;
            }
        }

        values = values.substring(0, values.length() - 1);
        columns = columns.substring(0, columns.length() - 1);

        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES(" + values + ")";

        System.out.println(sql);
        Statement statement = connection.createStatement();

        statement.executeUpdate(sql);
        connection.close();

    }

    private String nullIfNotFound(JSONObject object) {
        return object.has("value") ? object.getString("value") : "null";
    }

    public void addCompanyDatabase(Company company, JSONObject object) {

        Session session = conf.getSession();

        _logger.info("Dodawanie bazy firmy: " + company.getName());

        try {

            Database database = new Database(
                    object.getString("url"),
                    object.getString("port"),
                    object.getString("database"),
                    object.getString("login"),
                    object.getString("password")
            );

            List<Database> li = new ArrayList<Database>();
            li.add(database);
            company.setDatabase(li);

            session.save(database);
            session.getTransaction().commit();


        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();

        }
    }

    public Database getCompanyDatabase(Company company) {
        Session session = conf.getSession();

        _logger.info("Pobieranie bazy firmy: " + company.getName());
        Database database = null;
        try {

            Company c = session.load(Company.class, company.getId());
            database = c.getDatabase().get(0);

            session.getTransaction().commit();


        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();

        }
        return database;
    }
}
