package pl.sztyro.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.sztyro.main.model.Database;
import pl.sztyro.main.services.GoogleService;
import pl.sztyro.main.services.HibernateService;
import pl.sztyro.main.services.MainService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

//@CrossOrigin(origins = "https://nwtafront.herokuapp.com")
@CrossOrigin(origins = "https://localhost:4200")
@RestController
public class RESTController {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    MainService mainService;
    @Autowired
    GoogleService GService;
    @Autowired
    HibernateService hibernateService;

    /*@PostMapping("/databaseLogin")
    public Object login(@RequestBody String[] loginData, @RequestHeader("Authorization") String token) throws IllegalAccessException, InstantiationException, InvocationTargetException, SQLException {

        try {
            //BEZPIECZENSTWO///////////////////////////////////////////////////////////////////////////////////////

            String userMail = null;
            userMail = GService.verifyToken(token);
            if (userMail != null) {
                try {
                    int databaseId = hibernateService.getUserByMail(userMail).getDatabase().getDatabaseId();
                    hibernateService.setDatabase(databaseId, userMail, loginData[0], loginData[1], loginData[2], loginData[3], loginData[4]);
                } catch (Exception e) {
                    hibernateService.setDatabase(-1, userMail, loginData[0], loginData[1], loginData[2], loginData[3], loginData[4]);
                }

            } else {
                System.out.println("Mail is empty!");

            }
            UserDatabase database = hibernateService.getUserByMail(userMail).getDatabase();
            String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = '" + database.getDatabase() + "'";


            Connection connection = mainService.prepareConnection(token);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            List<String> answer = new ArrayList<>();
            while (resultSet.next()) {
                answer.add(resultSet.getString(1));
            }
            resultSet.close();
            statement.close();

            connection.close();

            return answer;

        } catch (Exception ex) {
            return ex.toString();
        } finally {

        }

    }
*/

    /*@GetMapping("/databases")
    public String isConnectedToDb(@RequestHeader("Authorization") String token) throws SQLException {

        try {
            //JdbcTemplate template = mainService.getTemplate(token);
            String userMail = GService.verifyToken(token);
            //int databaseId = hibernateService.getUserByMail(userMail).getDatabase().getDatabaseId();
            UserDatabase database = hibernateService.getUserByMail(userMail).getDatabase();
            return database.getDatabase();
        } catch (Exception e) {
            return null;
        }
    }
*/

    @RequestMapping("/getForeignKeyColumns")
    public List<String> getForeignKeysColumns(@RequestBody String table, @RequestHeader("Authorization") String token) throws SQLException {
        //jdbcTemplateObject.setDataSource(mainService.getDBSource(token));
        // oracle String sql = "select column_name from user_cons_columns where table_name = " + table + "  and constraint_name in " +
        //         "(select constraint_name from user_constraints where table_name = " + table + " and constraint_type = 'R' )";

        String sql = "select COLUMN_NAME, CONSTRAINT_NAME, REFERENCED_COLUMN_NAME, REFERENCED_TABLE_NAME " +
                "from information_schema.KEY_COLUMN_USAGE " +
                "where TABLE_NAME = " + table + " AND REFERENCED_COLUMN_NAME is NOT null";

        Connection connection = mainService.prepareConnection(token);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        List<String> answer = new ArrayList<>();
        while (resultSet.next()) {
            answer.add(resultSet.getString(1));
        }
        resultSet.close();
        statement.close();

        connection.close();

        return answer;
    }

    @RequestMapping("/getIdList")
    public List<String> getListOfIds(@RequestBody String[] table, @RequestHeader("Authorization") String token) throws SQLException {

        String sql1 = "SELECT REFERENCED_COLUMN_NAME, REFERENCED_TABLE_NAME FROM information_schema.KEY_COLUMN_USAGE WHERE COLUMN_NAME = '" + table[1] + "' AND TABLE_NAME = '" + table[0] + "'";
        Connection connection = mainService.prepareConnection(token);
        PreparedStatement statement = connection.prepareStatement(sql1);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        List<String[]> answer = new ArrayList<>();
        while (resultSet.next()) {
            answer.add(new String[]{resultSet.getString(1), resultSet.getString(2)});
        }
        resultSet.close();
        statement.close();


        String sql2 = "SELECT " + answer.get(0)[0] + " from " + answer.get(0)[1];
        PreparedStatement statement2 = connection.prepareStatement(sql2);
        statement2.execute();
        ResultSet resultSet2 = statement2.getResultSet();
        List<String> answer2 = new ArrayList<>();
        while (resultSet2.next()) {
            answer2.add(resultSet2.getString(1));
        }
        resultSet2.close();
        statement2.close();

        connection.close();

        return answer2;
    }

    @RequestMapping("/getTable")
    public List<Map<String, Object>> getTable(@RequestBody String tableName, @RequestHeader("Authorization") String token) throws SQLException {

        Connection connection = mainService.prepareConnection(token);
        /*Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hurtownia?user=root&password=&autoReconnect=false"
        );*/

        List<Map<String, Object>> answer = null;
        String sql = "Select * FROM " + tableName;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        try {
            answer = new ArrayList<Map<String, Object>>();
            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> map = new LinkedHashMap<>();
                System.out.println(resultSet.getMetaData().getColumnName(1) + " " + resultSet.getObject(1));
                for (int i = 1; i <= columnCount; i++) {


                    map.put(resultSet.getMetaData().getColumnName(i), resultSet.getObject(i));

                }

                answer.add(map);

            }

            resultSet.close();
            preparedStatement.close();

            connection.close();

            return answer;
        } finally {

        }


    }

    @RequestMapping("/execute")
    void execute(@RequestBody String sql, @RequestHeader("Authorization") String token) throws SQLException {

        //jdbcTemplateObject.setDataSource(mainService.getDBSource(token));
        //jdbcTemplateObject.execute(sql);
        Connection connection = mainService.prepareConnection(token);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        statement.close();
        connection.close();


    }

    @RequestMapping("/getDataType")
    public List<String> getDataType(@RequestBody String table, @RequestHeader("Authorization") String token) throws SQLException {
        //oracle String sql = "select data_type from user_tab_columns where table_name = '" + table + "'";
        //jdbcTemplateObject.setDataSource(mainService.getDBSource(token));
        String sql = "SELECT DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '" + table + "' order by ORDINAL_POSITION";
        Connection connection = mainService.prepareConnection(token);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();

        List<String> answer = new LinkedList<>();
        while (resultSet.next()) {
            answer.add(resultSet.getString(1));
        }
        resultSet.close();
        statement.close();

        connection.close();
        return answer;
    }

    @RequestMapping("/getPrimaryKey")
    public List<String> getPrimaryKey(@RequestBody String tableName, @RequestHeader("Authorization") String token) throws SQLException {
        //jdbcTemplateObject.setDataSource(mainService.getDBSource(token));
        //oracle String sql = "select column_name from user_cons_columns where constraint_name = (select constraint_name from user_constraints where table_name = '" + tableName + "' and constraint_type = 'P' and position = 1) ";
        String sql = "SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_NAME = '" + tableName + "' AND COLUMN_KEY = 'PRI'";

        Connection connection = mainService.prepareConnection(token);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        List<String> answer = new ArrayList<>();
        while (resultSet.next()) {
            answer.add(resultSet.getString(1));
        }
        resultSet.close();
        statement.close();

        connection.close();
        return answer;
    }

    @RequestMapping("/delete")
    public void deleteRow(@RequestBody String[] info, @RequestHeader("Authorization") String token) throws SQLException {
        //jdbcTemplateObject.setDataSource(mainService.getDBSource(token));
        String sql = "Delete from " + info[0] + " where " + info[1] + " = " + info[2];


        Connection connection = mainService.prepareConnection(token);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        statement.close();

        connection.close();
    }

    //@Autowired
    //@Qualifier("jdbcCustom")
    //private JdbcTemplate jdbcTemplateObject;

    /**
     * @param token
     * @return
     * @throws SQLException
     */
    /*@GetMapping("/getTableNames")
    public List<String> getTableNames(@RequestHeader("Authorization") String token) throws SQLException {
        //Oracle
        //String sql = "SELECT table_name FROM all_tables where owner = (select user from dual)";
        //MySql
        //JdbcTemplate template = null;
        try {
            //UserDatabase database = hibernateService.getUserByMail(GService.verifyToken(token)).getDatabase();
            //String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = '" + database.getDatabase() + "'";


            Connection connection = mainService.prepareConnection(token);
            //PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            List<String> answer = new ArrayList<>();
            while (resultSet.next()) {
                answer.add(resultSet.getString(1));
            }
            resultSet.close();
            statement.close();

            connection.close();

            return answer;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }*/
    @RequestMapping("/aaa")
    public String tryLogin(@RequestHeader("Authorization") String token) {
        //jwt.validateJwtToken(session);


        return "test";

    }


    @GetMapping("/token")
    public String token(@RequestHeader("Authorization") String token) {

        GService.verifyToken(token);
        return "token is: " + token;
    }

    /*@GetMapping("/")
    public String welcome() {
        return "app working";
    }
*/
    @GetMapping("/ttt")
    public String test() {
        return "ttt completed";
    }





    /*@GetMapping("/loginUser")
    public boolean loginUser(@RequestHeader("Authorization") String token) {
        String email = GService.verifyToken(token);
        System.out.println(email);
        User user = null;
        if (email != null) {
            try {
                //user = hibernateService.getUserByMail(email);
            } catch (Exception e) {
                System.out.println("nie ma uzytkownika");
                return false;
            } finally {
                if (user == null)
                    //hibernateService.addUser(email, "DEMO");

                return true;
            }


        } else
            return false;
    }*/

    @GetMapping("/currentDatabase")
    public Object getCurrentDatabase(@RequestHeader("Authorization") String token) {
        //UserDatabase db = hibernateService.getUserByMail(GService.verifyToken(token)).getDatabase();
        Database db = null;
        Map<String, String> answer = new LinkedHashMap<>();
        answer.put("url", db.getUrl());
        answer.put("user", db.getLogin());
        answer.put("port", db.getPort());
        answer.put("database", db.getDatabase());
        return answer;
    }

    @GetMapping("/randomNumber")
    public Object getRandomNumber() {
        Map<String, Object> answer = new LinkedHashMap<>();
        answer.put("value", Math.random());
        answer.put("time", new Date().getTime());
        return answer;
    }

    @RequestMapping("/login")
    public Object redirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/home");
        return null;
    }
}
