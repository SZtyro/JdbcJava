package pl.sztyro.BazaDanych;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class Controller{



    //@Bean
    JdbcTemplate jdbcTemplate(String[] loginData) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        final String driverClassName = "oracle.jdbc.driver.OracleDriver";
        //final String jdbcUrl = "jdbc:oracle:thin:@//155.158.112.45:1521/oltpstud";
        final DataSource dataSource = DataSourceBuilder.create().driverClassName(driverClassName).url("jdbc:oracle:thin:@//" + loginData[0]).username(loginData[1]).password(loginData[2]).build();

        return new JdbcTemplate(dataSource);
    }

    JdbcTemplate jdbcTemplate;


    @RequestMapping("/login")
    public String login(@RequestBody String[] loginData) throws IllegalAccessException, InstantiationException, InvocationTargetException {
    try{
        System.out.println("blblblblblblblb: " + loginData[0] + loginData[1]);
        jdbcTemplate = jdbcTemplate(loginData);
        System.out.println(jdbcTemplate.getDataSource().getConnection());
        return "acces";
    }catch(Exception ex) {
        return ex.toString();
    }

    }
    @RequestMapping ("/fkc")
    public List<String> getForeignKeysColumns(@RequestBody String table){
        RowMapper rowMapper = (ResultSet rs, int rowNum) -> rs.getString(1);
        List<String> answer = jdbcTemplate.query("select column_name from user_cons_columns where table_name = "+ table +"  and constraint_name in " +
                "(select constraint_name from user_constraints where table_name = "+ table +" and constraint_type = 'R' )", rowMapper);

        return answer;
    }

    @RequestMapping("/idList")
    public List<String> returnListOfIds(@RequestBody String[] table){
        //String table = "EMPLOYEES";
        //String column = "DEPARTMENT_ID";
        System.out.println("Nazwa tabeli: " + table[0]);
        System.out.println("Nazwa kolumny: " + table[1]);


        //String sql = "select table_name from user_constraints where constraint_name = (SELECT r_constraint_name FROM user_constraints where constraint_name = (select constraint_name from user_cons_columns where column_name = "+"'"+ table[1] +"'"+ " and table_name = "+"'"+ table[0] +"'"+ "))";
        String sql = "select column_name,table_name from user_cons_columns where constraint_name in " +
                "(SELECT r_constraint_name FROM user_constraints where  constraint_type in ('R','P') and constraint_name in "+
                "(select constraint_name from user_cons_columns where column_name = '"+table[1]+"' and table_name = '"+table[0]+"'))";
        System.out.println("Zapytanie: " + sql);
        //String sql = "select * from employees";
        RowMapper rowMapper = (ResultSet rs, int rowNum) -> {String[] tab = new String[2];
        tab[0] = rs.getString(1);
        tab[1] = rs.getString(2);
        return tab;

        };



        List<String[]>destinationTable = jdbcTemplate.query(sql,rowMapper);

        System.out.println("Odpowiedz: " + destinationTable);



        String sql2 = "select "+ destinationTable.get(0)[0] +" from " + destinationTable.get(0)[1];
        RowMapper rowMapper2 = (ResultSet rs, int rowNum) -> rs.getString(1);
        List<String> answer = jdbcTemplate.query(sql2,rowMapper2);

        System.out.println("id: " + answer);
        return answer;
    }


    @RequestMapping("/getTable")
    public List<Map<String,Object>> entities(@RequestBody String tableName){
        System.out.println(jdbcTemplate.queryForList("Select * from "+tableName));
        return jdbcTemplate.queryForList("Select * from "+tableName);
    }
    //public List<Employee> entities(){ return Employees; }


    @RequestMapping("/a")
        void addUser(@RequestBody String user) {
            System.out.println(user);
            jdbcTemplate.execute(user);

    }

    @RequestMapping("/getDataType")
    public List<String> returnDataType(@RequestBody String table){

        String sql = "select data_type from user_tab_columns where table_name = '"+table+"'" ;
        RowMapper rowMapper = (ResultSet rs, int rowNum) -> rs.getString(1);
        List<String> answer = jdbcTemplate.query(sql,rowMapper);

        System.out.println(answer.get(0));
        return answer;
    }

    @RequestMapping("/getPrimaryKey")
    public List<String> returnPrimaryKey(@RequestBody String tableName){
        String sql = "select column_name from user_cons_columns where constraint_name = (select constraint_name from user_constraints where table_name = '"+tableName+"' and constraint_type = 'P' and position = 1) ";
        RowMapper rowMapper = (ResultSet rs, int rowNum) -> rs.getString(1);
        List<String> answer = jdbcTemplate.query(sql,rowMapper);
        return answer;
    }

    @RequestMapping("/delete")
    public void deleteRow(@RequestBody String[] info){
        jdbcTemplate.execute("Delete from " + info[0] + " where " + info[1] + " = " +info[2]);
    }

    @RequestMapping("/getTableNames")
    public List<String> getTableNames() {
        String sql = "SELECT table_name FROM all_tables where owner = (select user from dual)";
        RowMapper rowMapper = (ResultSet rs, int rowNum) -> rs.getString(1);
        List<String> answer = jdbcTemplate.query(sql,rowMapper);
        return answer;
    }
}
