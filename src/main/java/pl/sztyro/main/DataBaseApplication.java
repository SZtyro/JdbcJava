package pl.sztyro.main;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

//@EnableEncryptableProperties
@SpringBootApplication
@ComponentScan(basePackages = {"pl.sztyro.hibernate", "pl.sztyro.main"})
@EntityScan("pl.sztyro.hibernate")
public class DataBaseApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(DataBaseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {




	}
}
