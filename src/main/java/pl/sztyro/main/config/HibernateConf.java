package pl.sztyro.main.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Konfiguracja Hibernate
 */
@Configuration
@EnableTransactionManagement
public class HibernateConf {

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(
                new String[]{"pl.sztyro.main.model"});
        sessionFactory.setHibernateProperties(hibernateProperties());


        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        Dotenv dotenv = Dotenv.load();

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(dotenv.get("url"));
        dataSource.setUsername(dotenv.get("DB_USER"));
        dataSource.setPassword(dotenv.get("DB_PASSWORD"));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                "hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty(
                "hibernate.dialect", "org.hibernate.dialect.MariaDB103Dialect");

        return hibernateProperties;
    }

    /**
     * Zwraca otwartą sesję hibernate
     *
     * @return Session
     */
    public Session getSession() {
        SessionFactory sessionFactory = this.sessionFactory().getObject();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }
}

/*@Component
public class HibernateFactory {


    *//* private Configuration getHibernateConfig() {
         Configuration configuration = new Configuration();
         //configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/companyusers");
         configuration.setProperty("hibernate.connection.url", "jdbc:mysql://sql.adammalota.nazwa.pl/adammalota_nwta");
         //configuration.setProperty("hibernate.connection.username", "root");
         configuration.setProperty("hibernate.connection.username", "adammalota_nwta");
         //configuration.setProperty("hibernate.connection.password", "");
         configuration.setProperty("hibernate.connection.password", "Nwta123$");
         configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
         configuration.setProperty("connection.driver_class", "com.mysql.jdbc.Driver");
         configuration.setProperty("hibernate.hbm2ddl.auto", "update");
         //configuration.setProperty("hibernate.connection.autocommit", "true");
         //configuration.setProperty("spring.jpa.generate-ddl", "true");
         configuration.addAnnotatedClass(User.class);
         configuration.addAnnotatedClass(UserDatabase.class);
         return configuration;
     }

     public SessionFactory getSessionFactory() {
         StandardServiceRegistry registry = new StandardServiceRegistryBuilder().
                 applySettings(getHibernateConfig().getProperties()).build();
         return getHibernateConfig().buildSessionFactory(registry);
     }*//*
 *//* public SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        Dotenv dotenv = Dotenv.load();

        configuration.setProperty("hibernate.connection.url", dotenv.get("url"));
        configuration.setProperty("hibernate.connection.username", dotenv.get("DB_USER"));
        configuration.setProperty("hibernate.connection.password", dotenv.get("DB_PASSWORD"));
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");



        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(UserDatabase.class);
        configuration.addAnnotatedClass(UserCompany.class);

        StandardServiceRegistryBuilder registryBuilder =
                new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(registryBuilder.build());
        return sessionFactory;
    }*//*
}*/
