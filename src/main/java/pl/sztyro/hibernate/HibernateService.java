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
}
