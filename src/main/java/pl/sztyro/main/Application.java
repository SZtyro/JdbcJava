package pl.sztyro.main;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

//@EnableEncryptableProperties
@SpringBootApplication
@EntityScan(basePackages = {"pl.sztyro.main.model"})
public class Application implements CommandLineRunner {

    @Autowired
    private SessionFactory sessionFactory;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


    }
}
