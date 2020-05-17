package pl.sztyro.BazaDanych;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.sztyro.hibernate.HibernateService;
import pl.sztyro.hibernate.User;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class Controller {

    @Autowired
    private FileService fileService;
    JdbcTemplate jdbcTemplate;

    @PostMapping(value = "/uploadFiles")
    @ResponseStatus(HttpStatus.OK)
    public void handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        fileService.storeFile(file);
    }

    @RequestMapping("/getFiles")
    public List<String> getFiles() {
        List<String> fileNames = new ArrayList<String>();
        for (File f : fileService.filesInFolder) {
            fileNames.add(f.getName());
        }
        return fileNames;
    }

    JdbcTemplate getJdbcTemplate(String[] loginData) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        // oracle final String driverClassName = "oracle.jdbc.driver.OracleDriver";
        final String driverClassName = "com.mysql.cj.jdbc.Driver";
        //final String jdbcUrl = "jdbc:oracle:thin:@//155.158.112.45:1521/oltpstud";
        // oracle final DataSource dataSource = DataSourceBuilder.create().driverClassName(driverClassName).url("jdbc:oracle:thin:@//" + loginData[0]).username(loginData[1]).password(loginData[2]).build();
        final DataSource dataSource = DataSourceBuilder.create().driverClassName(driverClassName).url("jdbc:mysql://" + loginData[0] + "?serverTimezone=UTC").username(loginData[1]).password(loginData[2]).build();

        return new JdbcTemplate(dataSource);
    }

    @RequestMapping("/databaseLogin")
    public String login(@RequestBody String[] loginData) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        try {
            jdbcTemplate = getJdbcTemplate(loginData);
            jdbcTemplate.getDataSource().getConnection();
            return "acces";
        } catch (Exception ex) {
            return ex.toString();
        }

    }

    @RequestMapping("/getForeignKeyColumns")
    public List<String> getForeignKeysColumns(@RequestBody String table) {
        RowMapper rowMapper = (ResultSet rs, int rowNum) -> rs.getString(1);
        // oracle String sql = "select column_name from user_cons_columns where table_name = " + table + "  and constraint_name in " +
        //         "(select constraint_name from user_constraints where table_name = " + table + " and constraint_type = 'R' )";
        String sql = "select COLUMN_NAME, CONSTRAINT_NAME, REFERENCED_COLUMN_NAME, REFERENCED_TABLE_NAME " +
                "from information_schema.KEY_COLUMN_USAGE " +
                "where TABLE_NAME = " + table + " AND REFERENCED_COLUMN_NAME is NOT null";


        List<String> answer = jdbcTemplate.query(sql, rowMapper);

        return answer;
    }

    @RequestMapping("/getIdList")
    public List<String> getListOfIds(@RequestBody String[] table) {
        /*String sql = "select column_name,table_name from user_cons_columns where constraint_name in " +
                "(SELECT r_constraint_name FROM user_constraints where  constraint_type in ('R','P') and constraint_name in " +
                "(select constraint_name from user_cons_columns where column_name = '" + table[1] + "' and table_name = '" + table[0] + "'))";

        RowMapper rowMapper = (ResultSet rs, int rowNum) -> {
            String[] tab = new String[2];
            tab[0] = rs.getString(1);
            tab[1] = rs.getString(2);
            return tab;

        };
        List<String[]> destinationTable = jdbcTemplate.query(sql, rowMapper);
        String sql2 = "select " + destinationTable.get(0)[0] + " from " + destinationTable.get(0)[1];*/

        String sql1 = "SELECT REFERENCED_COLUMN_NAME, REFERENCED_TABLE_NAME FROM information_schema.KEY_COLUMN_USAGE WHERE COLUMN_NAME = '" + table[1] + "' AND TABLE_NAME = '" + table[0] + "'";
        RowMapper rowMapper2 = (ResultSet rs, int rowNum) -> {
            String[] tab = new String[2];
            tab[0] = rs.getString(1);
            tab[1] = rs.getString(2);
            return tab;
        };

        List<String[]> answer = jdbcTemplate.query(sql1, rowMapper2);
        String sql2 = "SELECT " + answer.get(0)[0] + " from " + answer.get(0)[1];

        RowMapper rowMapper3 = (ResultSet rs, int rowNum) -> rs.getString(1);
        List<String> answer2 = jdbcTemplate.query(sql2, rowMapper3);
        return answer2;
    }

    @RequestMapping("/getTable")
    public List<Map<String, Object>> getTable(@RequestBody String tableName) {

        return jdbcTemplate.queryForList("Select * from " + tableName);
    }

    @RequestMapping("/execute")
    void execute(@RequestBody String sql) {
        jdbcTemplate.execute(sql);

    }

    @RequestMapping("/getDataType")
    public List<String> getDataType(@RequestBody String table) {
        //oracle String sql = "select data_type from user_tab_columns where table_name = '" + table + "'";
        String sql = "SELECT DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '" + table + "'";
        RowMapper rowMapper = (ResultSet rs, int rowNum) -> rs.getString(1);
        List<String> answer = jdbcTemplate.query(sql, rowMapper);
        return answer;
    }

    @RequestMapping("/getPrimaryKey")
    public List<String> getPrimaryKey(@RequestBody String tableName) {
        //oracle String sql = "select column_name from user_cons_columns where constraint_name = (select constraint_name from user_constraints where table_name = '" + tableName + "' and constraint_type = 'P' and position = 1) ";
        String sql = "SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_NAME = '" + tableName + "' AND COLUMN_KEY = 'PRI'";
        RowMapper rowMapper = (ResultSet rs, int rowNum) -> rs.getString(1);
        List<String> answer = jdbcTemplate.query(sql, rowMapper);
        return answer;
    }

    @RequestMapping("/delete")
    public void deleteRow(@RequestBody String[] info) {
        System.out.println("Delete from " + info[0] + " where " + info[1] + " = " + info[2]);
        jdbcTemplate.execute("Delete from " + info[0] + " where " + info[1] + " = " + info[2]);

    }

    @RequestMapping("/getTableNames")
    public List<String> getTableNames() throws SQLException {
        //Oracle
        //String sql = "SELECT table_name FROM all_tables where owner = (select user from dual)";
        //MySql

        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'hurtownia'";
        RowMapper rowMapper = (ResultSet rs, int rowNum) -> rs.getString(1);
        List<String> answer = jdbcTemplate.query(sql, rowMapper);
        return answer;
    }


    @RequestMapping("/aaa")
    public String tryLogin(@RequestHeader("Authorization") String token) {
        //jwt.validateJwtToken(session);


        return "test";

    }

    //@Autowired
    //JwtUtils jwt;
    @Autowired
    GoogleService GService;

    @Autowired
    HibernateService hibernateService;

    @GetMapping("/token")
    public String token(@RequestHeader("Authorization") String token) {

        GService.verifyToken(token);
        return "token is: " + token;
    }

    @GetMapping("/ttt")
    public String test() {
        return "ttt completed";
    }

    @GetMapping("/loginUser")
    public boolean loginUser(@RequestHeader("Authorization") String token) {
        String email = GService.verifyToken(token);
        User user = null;
        if (email != null) {
            try {
                user = hibernateService.getUserByMail(email);
            } catch (Exception e) {
                System.out.println("nie ma uzytkownika");
                return false;
            } finally {
                if (user == null)
                    hibernateService.addUser(email, "DEMO");

                return true;
            }


        } else
            return false;
    }
}
