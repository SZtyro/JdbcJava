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
import java.util.Map;


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
	List<Map<String,Object>>test;


	@Override
	public void run(String... args) throws Exception {



	}
}
