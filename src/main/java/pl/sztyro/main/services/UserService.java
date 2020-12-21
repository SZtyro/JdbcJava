package pl.sztyro.main.services;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sztyro.main.controllers.HibernateConf;
import pl.sztyro.main.exceptions.NotFoundException;
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
            session.getTransaction().rollback();
            session.close();

            throw new NotFoundException("User not found");
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

    public java.util.List<pl.sztyro.main.model.Company> getUserCompanies(String mail) throws NotFoundException {
        User user = getUser(mail);
        return user.getCompanies();
    }
}
