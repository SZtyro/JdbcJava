package pl.sztyro.main.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sztyro.main.config.HibernateConf;
import pl.sztyro.main.exceptions.NoPermissionException;
import pl.sztyro.main.exceptions.NotFoundException;
import pl.sztyro.main.model.Company;
import pl.sztyro.main.model.Institution;
import pl.sztyro.main.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class InstitutionService {

    private static final Logger _logger = LoggerFactory.getLogger(InstitutionService.class);

    @Autowired
    HibernateConf conf;

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserService userService;

    public Institution createInstitution(String mail) throws NotFoundException {

        _logger.info("Tworzenie placówki");

        Session session = conf.getSession();
        User user = null;
        Company selectedCompany = null;
        Institution institution = null;
        try {
            user = session.load(User.class, mail);
            selectedCompany = user.getSelectedCompany();
            institution = new Institution(selectedCompany);
            session.save(institution);
            session.update(selectedCompany);
            session.getTransaction().commit();

        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
            if (selectedCompany == null) {
                throw new NotFoundException("User has no selected company");
            } else {
                return institution;
            }
        }


    }

    public Institution getInstitution(Long id) {

        _logger.info("Pobieranie placówki o id: " + id);

        Session session = conf.getSession();
        try {
            return session.load(Institution.class, id);

        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public List<Institution> getInstitutions(Company company) throws NotFoundException {
        Session session = conf.getSession();
        List<Institution> institutions = null;

        try {

            Query query = session.createQuery("from Institution i where i.company.id=" + company.getId());
            institutions = query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
            if (institutions == null) {
                throw new NotFoundException("User has no institutions");
            } else {
                return institutions;
            }
        }

    }

    public void addInstitution(String mail, Institution institution) throws NotFoundException {
        Session session = conf.getSession();

        _logger.info("Dodawanie placówki: " + institution.getName());

        try {
            User user = session.load(User.class, mail);
            Company selectedCompany = user.getSelectedCompany();
            if (selectedCompany == null) {
                _logger.error("Użytkownik nie ma wybranej firmy");
                throw new NotFoundException("User has no selected company");
            } else {
                institution.setCompany(selectedCompany);
                //selectedCompany.addInstitution(institution);
            }
            System.out.println("cmp: " + institution.getCompany());
            session.update(selectedCompany);
            session.update(institution);
            session.getTransaction().commit();

        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public void updateInstitution(String mail, Institution newInstitution) throws NoPermissionException, NotFoundException {
        Session session = conf.getSession();


        User user = session.load(User.class, mail);
        Institution institution;

        Gson obj = new Gson();
        JsonObject params = new JsonObject();
        params.addProperty("name", newInstitution.getName());


        if (newInstitution.getId() == 0) {
            if (newInstitution.getCompany() == null) {
                Company selectedCompany = user.getSelectedCompany();
                if (selectedCompany == null) {
                    _logger.error("Użytkownik nie ma wybranej firmy");
                    throw new NotFoundException("User has no selected company");
                } else {
                    newInstitution.setCompany(selectedCompany);
                }
            }
            session.save(newInstitution);
            session.getTransaction().commit();
            session.close();

            notificationService.createNotification("BOT", "notification.institution.created", obj.toJson(params), new ArrayList<User>(Arrays.asList(userService.getUser(mail))));
        } else {
            institution = session.load(Institution.class, newInstitution.getId());

            if (user.getMail().equals(institution.getCompany().getOwner().getMail())) {
                institution.merge(newInstitution);
                session.update(institution);
                session.getTransaction().commit();
                session.close();

                notificationService.createNotification("BOT", "notification.institution.updated", obj.toJson(params), new ArrayList<User>(Arrays.asList(userService.getUser(mail))));
            } else {
                session.getTransaction().rollback();
                session.close();
                throw new NoPermissionException("User is not owner of company");
            }
        }


    }

    public void addUserToInstitution(String mail, Long id) {
        Session session = conf.getSession();

        _logger.info("Dodawanie użytkownika: " + mail + " do placówki.");

        try {
            User user = session.load(User.class, mail);
            Institution institution = session.load(Institution.class, id);
            institution.addEmployee(user);

            session.update(institution);
            session.getTransaction().commit();

        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public void deleteInstitution(String mail, Long id) {
        Session session = conf.getSession();


        try {

            Institution institution = session.load(Institution.class, id);
            session.delete(institution);
            session.getTransaction().commit();

            Gson obj = new Gson();
            JsonObject params = new JsonObject();
            params.addProperty("name", institution.getName());
            _logger.info("Usunięto placówkę o nazwie: " + institution.getName());
            notificationService.createNotification("BOT", "notification.institution.deleted", obj.toJson(params), new ArrayList<User>(Arrays.asList(userService.getUser(mail))));

        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

}
