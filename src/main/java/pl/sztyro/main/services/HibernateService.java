package pl.sztyro.main.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.stereotype.Service;
import pl.sztyro.main.config.HibernateConf;
import pl.sztyro.main.model.User;
import pl.sztyro.main.model.Database;


@Service
public class HibernateService {

    private HibernateConf hibernateFactory;
    private Session session;
    private SessionFactory factory;

    public HibernateService() {
        //hibernateFactory = new HibernateFactory();
        //factory = hibernateFactory.getSessionFactory();
        //session = factory.openSession();
    }

    /*public User getUserByMail(String mail) {
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();


        User user = null;
        try {
            user = session.load(User.class, mail);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
            transaction.rollback();
        } finally {
            session.close();
        }
        return user;
    }

    public void addUser(String mail, String pack) {
        Session session = hibernateFactory.getSessionFactory().openSession();

        *//*try {
            session = factory.getCurrentSession();
        } catch (Exception e) {
            session = factory.openSession();
        }*//*
        Transaction transaction = session.beginTransaction();
        try {
            session.save(new User(mail, pack));
            session.getTransaction().commit();
        } catch (Exception ex) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }*/

    public User setDatabase(int id, String mail, String url, String port, String database, String login, String password) {
        //Session session = hibernateFactory.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();
        User user = null;
        try {
            user = session.load(User.class, mail);
            AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
            textEncryptor.setPassword("dev-env-secret");
            String encryptedPassword = textEncryptor.encrypt(password);
            System.out.println("zaszyfrowane haslo: " + encryptedPassword);
            System.out.println("odszyfrowane haslo: " + textEncryptor.decrypt(encryptedPassword));
            if (id >= 0) {
                Database base = session.load(Database.class, id);
                base.setUrl(url);
                base.setPort(port);
                base.setDatabase(database);
                base.setLogin(login);
                base.setPassword(encryptedPassword);
                //user.setUserDatabase(new UserDatabase(id,url,port,database,login,encryptedText));
            } else
                user.setUserDatabase(new Database(url, port, database, login, encryptedPassword));

            session.getTransaction().commit();
        } catch (Exception ex) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return user;
    }


}
