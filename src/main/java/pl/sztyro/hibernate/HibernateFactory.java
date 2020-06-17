package pl.sztyro.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;
import pl.sztyro.hibernate.entities.User;
import pl.sztyro.hibernate.entities.UserDatabase;

@Component
public class HibernateFactory {

    private Configuration getHibernateConfig() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/companyusers");
        //configuration.setProperty("hibernate.connection.url", "jdbc:mysql://sql.adammalota.nazwa.pl/adammalota_nwta");
        configuration.setProperty("hibernate.connection.username", "root");
        //configuration.setProperty("hibernate.connection.username", "adammalota_nwta");
        configuration.setProperty("hibernate.connection.password", "");
        //configuration.setProperty("hibernate.connection.password", "Nwta123$");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
        configuration.setProperty("connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.setProperty("hibernate.connection.autocommit", "true");
        //configuration.setProperty("spring.jpa.generate-ddl", "true");
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(UserDatabase.class);
        return configuration;
    }

    public SessionFactory getSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().
                applySettings(getHibernateConfig().getProperties()).build();
        return getHibernateConfig().buildSessionFactory(registry);
    }
}
