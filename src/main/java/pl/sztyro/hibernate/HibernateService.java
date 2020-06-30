package pl.sztyro.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.stereotype.Service;
import pl.sztyro.hibernate.entities.User;
import pl.sztyro.hibernate.entities.UserDatabase;


@Service
public class HibernateService {

    private HibernateFactory hibernateFactory;
    private Session session;
    private SessionFactory factory;

    public HibernateService() {
        hibernateFactory = new HibernateFactory();
        //factory = hibernateFactory.getSessionFactory();
        //session = factory.openSession();
    }

    public User getUserByMail(String mail) {
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

        /*try {
            session = factory.getCurrentSession();
        } catch (Exception e) {
            session = factory.openSession();
        }*/
        Transaction transaction = session.beginTransaction();
        try {
            session.save(new User(mail, pack));
            session.getTransaction().commit();
        } catch (Exception ex) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public void updateDashboard(String mail, String dashboard) {
        Session session = hibernateFactory.getSessionFactory().openSession();

        System.out.println(dashboard);
        Transaction transaction = session.beginTransaction();
        try {
            User user = (User) session.load(User.class, mail);
            user.setDashboard(dashboard);
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception ex) {
            transaction.rollback();
        } finally {
            session.close();
        }

    }

    public String getDashboard(String mail) {
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String dashboard = "";
        try {
            User user = (User) session.load(User.class, mail);
            dashboard = user.getDashboard();

            session.getTransaction().commit();
        } catch (Exception ex) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return dashboard;

    }

    public User setDatabase(int id, String mail, String url, String port, String database, String login, String password) {
        Session session = hibernateFactory.getSessionFactory().openSession();

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
                UserDatabase base = session.load(UserDatabase.class, id);
                base.setUrl(url);
                base.setPort(port);
                base.setDatabase(database);
                base.setLogin(login);
                base.setPassword(encryptedPassword);
                //user.setUserDatabase(new UserDatabase(id,url,port,database,login,encryptedText));
            } else
                user.setUserDatabase(new UserDatabase(url, port, database, login, encryptedPassword));

            session.getTransaction().commit();
        } catch (Exception ex) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return user;
    }


}
