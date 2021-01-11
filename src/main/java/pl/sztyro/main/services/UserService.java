package pl.sztyro.main.services;

import org.hibernate.Hibernate;
import org.hibernate.Session;
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

    public User getUser(String mail) throws NotFoundException {
        Session session = conf.getSession();


        User u = session.get(User.class, mail);
        if (u == null) {
            //session.getTransaction().rollback();


            //throw new NotFoundException("User not found");
            addUser(mail);
            u = session.get(User.class, mail);

            Hibernate.initialize(u.getSelectedCompany());
            session.getTransaction().commit();
            session.close();
            return u;
        } else {
            session.getTransaction().commit();
            session.close();
            return u;
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

    public Company selectCompany(String mail, Long id) throws NotFoundException {
        _logger.info("Wybór firmy: " + mail);

        Session session = conf.getSession();


        User user = session.get(User.class, mail);
        Company company = session.get(Company.class, id);
        if (user == null) {
            session.getTransaction().rollback();
            session.close();

            throw new NotFoundException("User not found");
        } else {
            if (company == null) {
                session.getTransaction().rollback();
                session.close();

                throw new NotFoundException("Company not found");
            } else {
                session.getTransaction().commit();
                session.close();

                return company;
            }
        }


    }

    public void updateUser(String mail, User user) {
        _logger.info("Zaktualizowano użytkownika: " + user.getMail());

        Session session = conf.getSession();


        try {
            User u = session.load(User.class, mail);
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
