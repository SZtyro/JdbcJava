package pl.sztyro.main.services;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sztyro.main.config.HibernateConf;
import pl.sztyro.main.exceptions.NotFoundException;
import pl.sztyro.main.model.Company;
import pl.sztyro.main.model.User;

@Service
public class UserService {

    private static final Logger _logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    HibernateConf conf;

    @Autowired
    UserService userService;

    /**
     * @param mail
     * @return
     * @throws NotFoundException
     * @deprecated
     */
    public User getUser(String mail) throws NotFoundException {
        Session session = conf.getSession();

        _logger.info("Pobieranie użytkownika: " + mail);
        User u = null;
        try {

            u = userService.getUserByMail(mail);


            Hibernate.initialize(u.getSelectedCompany());
            session.getTransaction().commit();

        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();

        } finally {
            session.close();
            if(u == null)
                throw new NotFoundException("toasts.user.notfound");
            return u;
        }
    }

    public User getUserByMail(String mail) {
        _logger.info("Pobieranie użytkownika: " + mail);
        Session session = conf.getSession();
        User user = null;

        try {
            Query<User> query = session.createQuery("FROM User u where u.mail='" + mail + "'");
            user = query.uniqueResult();

            session.getTransaction().commit();
        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
            return user;
        }
    }

    public void addUser(String mail) {
        _logger.info("Rejestracja użytkownika: " + mail);
        Session session = conf.getSession();

        try {
            session.save(new User(mail));
            session.getTransaction().commit();
        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public void selectCompany(String mail, Long id) throws NotFoundException {
        _logger.info("Wybór firmy: " + mail);

        Session session = conf.getSession();
        Company company = null;
        try {
            User user = userService.getUserByMail(mail);
            company = session.load(Company.class, id);

            user.setSelectedCompany(company);
            session.update(user);
            session.getTransaction().commit();

        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
            //return company;
        }


    }

    public void updateUser(String mail, User user) {
        _logger.info("Zaktualizowano użytkownika: " + user.getMail());

        Session session = conf.getSession();


        try {
            User u = userService.getUserByMail(mail);
            u.merge(user);
            session.update(u);
            session.getTransaction().commit();
        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

    }

}
