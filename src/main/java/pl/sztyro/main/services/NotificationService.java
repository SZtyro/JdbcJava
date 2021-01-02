package pl.sztyro.main.services;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sztyro.main.config.HibernateConf;
import pl.sztyro.main.model.Company;
import pl.sztyro.main.model.Notification;
import pl.sztyro.main.model.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class NotificationService {

    private static final Logger _logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    HibernateConf conf;

    public Notification createNotification(String author, String message, String params, List<User> involved) {

        Session session = conf.getSession();
        Notification notification = null;
        try {

            notification = new Notification(author, message, params, involved);
            session.save(notification);
            session.getTransaction().commit();

        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }

        return notification;
    }

    public List<Notification> getUserNotifications(String mail) {

        Session session = conf.getSession();
        List<Notification> notifications = null;
        try {

            //Query query = session.createQuery("FROM \'notification\' n INNER JOIN notification_user u ON n.id = u.Notification_id WHERE u.involved_user_mail = 'fabixd123@gmail.com' ORDER BY created");

            Query query = session.createQuery("FROM Notification as n where \'" + mail + "\' in elements(n.involved)");
            notifications = query.list();

            session.getTransaction().commit();

        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }

        return notifications;
    }
}
