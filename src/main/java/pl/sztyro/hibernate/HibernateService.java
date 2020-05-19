package pl.sztyro.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

@Service
public class HibernateService {

    private HibernateFactory hibernateFactory;

    public HibernateService() {
        hibernateFactory = new HibernateFactory();
    }

    public User getUserByMail(String mail) {
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = null;
        try {
            user = session.load(User.class, mail);
            session.getTransaction().commit();
        } catch (Exception ex) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return user;
    }

    public void addUser(String mail, String pack) {

        Session session = hibernateFactory.getSessionFactory().openSession();
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
        Transaction transaction = session.beginTransaction();
        try {
            User user = (User)session.load(User.class, mail);
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
            User user = (User)session.load(User.class, mail);
            dashboard = user.getDashboard();

            session.getTransaction().commit();
        } catch (Exception ex) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return dashboard;

    }

}
