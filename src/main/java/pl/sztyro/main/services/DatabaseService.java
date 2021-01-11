package pl.sztyro.main.services;

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
            table.put("nullable", result.getString("IS_NULLABLE") == "YES");
            table.put("dataType", result.getString("DATA_TYPE"));
            table.put("autoIncrement", result.getString("EXTRA").contains("auto_increment"));

            array.put(table);
        }
        connection.close();

        System.out.println(array.toString(2));
        return array.toString();
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
