package pl.sztyro.BazaDanych;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import pl.sztyro.Entities.Employee;

import java.sql.ResultSet;
import java.util.List;


@SpringBootApplication
public class DataBaseApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(DataBaseApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DataBaseApplication.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	private klasaBazy baza;


	List<Employee> Employees;



	@Override
	public void run(String... args) throws Exception {

		String sql = "select * from employees";
		//baza.listaStringow = jdbcTemplate.query("Select * from employees",(rs,rowNum)-> new Employee(rs.getLong("EMPLOYEE_ID")));
		//Entities = jdbcTemplate.queryForList(sql);

		RowMapper rowMapper = (ResultSet rs, int rowNum) -> rs.getString(1);
		Employees = (jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> new Employee(
					rs.getLong(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getString(5),
					rs.getDate(6).toLocalDate(),
					rs.getString(7),
					rs.getInt(8),
					rs.getDouble(9),
					rs.getLong(10),
					rs.getLong(11)
			)));


	}
}
