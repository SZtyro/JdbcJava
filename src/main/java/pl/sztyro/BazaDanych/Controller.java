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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
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
        final String driverClassName = "oracle.jdbc.driver.OracleDriver";
        //final String jdbcUrl = "jdbc:oracle:thin:@//155.158.112.45:1521/oltpstud";
        final DataSource dataSource = DataSourceBuilder.create().driverClassName(driverClassName).url("jdbc:oracle:thin:@//" + loginData[0]).username(loginData[1]).password(loginData[2]).build();

        return new JdbcTemplate(dataSource);
    }

    @RequestMapping("/sslogin")
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
        List<String> answer = jdbcTemplate.query("select column_name from user_cons_columns where table_name = " + table + "  and constraint_name in " +
                "(select constraint_name from user_constraints where table_name = " + table + " and constraint_type = 'R' )", rowMapper);

        return answer;
    }

    @RequestMapping("/getIdList")
    public List<String> getListOfIds(@RequestBody String[] table) {
        String sql = "select column_name,table_name from user_cons_columns where constraint_name in " +
                "(SELECT r_constraint_name FROM user_constraints where  constraint_type in ('R','P') and constraint_name in " +
                "(select constraint_name from user_cons_columns where column_name = '" + table[1] + "' and table_name = '" + table[0] + "'))";

        RowMapper rowMapper = (ResultSet rs, int rowNum) -> {
            String[] tab = new String[2];
            tab[0] = rs.getString(1);
            tab[1] = rs.getString(2);
            return tab;

        };
        List<String[]> destinationTable = jdbcTemplate.query(sql, rowMapper);
        String sql2 = "select " + destinationTable.get(0)[0] + " from " + destinationTable.get(0)[1];
        RowMapper rowMapper2 = (ResultSet rs, int rowNum) -> rs.getString(1);
        List<String> answer = jdbcTemplate.query(sql2, rowMapper2);
        return answer;
    }

    @RequestMapping("/getTable")
    public List<Map<String, Object>> getTable(@RequestBody String tableName) {

        return null;
    }

    @RequestMapping("/execute")
    void execute(@RequestBody String sql) {
        jdbcTemplate.execute(sql);

    }

    @RequestMapping("/getDataType")
    public List<String> getDataType(@RequestBody String table) {
        String sql = "select data_type from user_tab_columns where table_name = '" + table + "'";
        RowMapper rowMapper = (ResultSet rs, int rowNum) -> rs.getString(1);
        List<String> answer = jdbcTemplate.query(sql, rowMapper);
        return answer;
    }

    @RequestMapping("/getPrimaryKey")
    public List<String> getPrimaryKey(@RequestBody String tableName) {
        String sql = "select column_name from user_cons_columns where constraint_name = (select constraint_name from user_constraints where table_name = '" + tableName + "' and constraint_type = 'P' and position = 1) ";
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
    public List<String> getTableNames() {
        String sql = "SELECT table_name FROM all_tables where owner = (select user from dual)";
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
    public String loginUser(@RequestHeader("Authorization") String token) {
        String email = GService.verifyToken(token);
        User user = null;
        if (email != null) {
            try {
                user = hibernateService.getUserByMail(email);
            } catch (Exception e) {
                System.out.println("nie ma uzytkownika");
            } finally {
                if (user == null)
                    hibernateService.addUser(email, "DEMO");
            }

            return "Dostep";
        } else
            return "Token za stary";
    }
}
