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

    @Autowired
    CompanyService companyService;

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
//            if (u == null) {
//                addUser(mail);
//                u = userService.getUserByMail(mail);
//            }

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
            user = session.load(User.class, query.uniqueResult().getId());
            //Hibernate.initialize(user.getSelectedCompany());

            session.getTransaction().commit();
        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
            return user;
        }
    }

    public void addUser(String mail, String firstname, String surname) {
        _logger.info("Rejestracja użytkownika: " + mail);
        Session session = conf.getSession();

        try {
            if (getUserByMail(mail) == null)
                session.save(new User(mail,firstname,surname));
            else
                _logger.info("Użytkownik istnieje w bazie: " + mail);
            session.getTransaction().commit();
        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public void addUser(String mail) {
        _logger.info("Rejestracja użytkownika: " + mail);
        Session session = conf.getSession();

        try {
            if (getUserByMail(mail) == null)
                session.save(new User(mail));
            else
                _logger.info("Użytkownik istnieje w bazie: " + mail);
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

        User user = userService.getUserByMail(mail);
        Session session = conf.getSession();
        Company company = null;
        try {

            company = session.load(Company.class, id);

            //companyService.selectCompany(mail,id);

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
