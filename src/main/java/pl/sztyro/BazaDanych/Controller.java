package pl.sztyro.BazaDanych;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;
import pl.sztyro.Entities.Employee;

import javax.servlet.http.HttpServletRequest;
import java.sql.Array;
import java.sql.ClientInfoStatus;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class Controller{

    @Autowired
    private klasaBazy baza;


    //tymczasowo
    @Autowired
    private DataBaseApplication main;


    @RequestMapping ("/fkc")
    public List<String> getForeignKeysColumns(@RequestBody String table){
        RowMapper rowMapper = (ResultSet rs, int rowNum) -> rs.getString(1);
        List<String> answer = main.jdbcTemplate.query("select column_name from user_cons_columns where table_name = "+ table +"  and constraint_name in " +
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
        String sql = "select column_name,table_name from user_cons_columns where constraint_name = " +
                "(SELECT r_constraint_name FROM user_constraints where  constraint_type in ('R','P') and constraint_name in "+
                "(select constraint_name from user_cons_columns where column_name = '"+table[1]+"' and table_name = '"+table[0]+"'))";
        System.out.println("Zapytanie: " + sql);
        //String sql = "select * from employees";
        RowMapper rowMapper = (ResultSet rs, int rowNum) -> {String[] tab = new String[2];
        tab[0] = rs.getString(1);
        tab[1] = rs.getString(2);
        return tab;

        };



        List<String[]>destinationTable = main.jdbcTemplate.query(sql,rowMapper);

        System.out.println("Odpowiedz: " + destinationTable);



        String sql2 = "select "+ destinationTable.get(0)[0] +" from " + destinationTable.get(0)[1];
        RowMapper rowMapper2 = (ResultSet rs, int rowNum) -> rs.getString(1);
        List<String> answer = main.jdbcTemplate.query(sql2,rowMapper2);

        System.out.println("id: " + answer);
        return answer;
    }


    @RequestMapping("/getTable")
    public List<Map<String,Object>> entities(@RequestBody String tableName){
        return main.jdbcTemplate.queryForList("Select * from "+tableName); }
    //public List<Employee> entities(){ return main.Employees; }


    @RequestMapping("/a")
        void addUser(@RequestBody String user) {
            System.out.println(user);
            main.jdbcTemplate.execute(user);

    }

    @RequestMapping("/getDataType")
    public List<String> returnDataType(@RequestBody String table){

        String sql = "select data_type from user_tab_columns where table_name = '"+table+"'" ;
        RowMapper rowMapper = (ResultSet rs, int rowNum) -> rs.getString(1);
        List<String> answer = main.jdbcTemplate.query(sql,rowMapper);

        System.out.println(answer.get(0));
        return answer;
    }
}
