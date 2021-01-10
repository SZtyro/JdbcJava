package pl.sztyro.main.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sztyro.main.config.HibernateConf;
import pl.sztyro.main.exceptions.NotFoundException;
import pl.sztyro.main.model.Company;
import pl.sztyro.main.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CompanyService {

    private static final Logger _logger = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    HibernateConf conf;

    @Autowired
    NotificationService notificationService;

    public Company createCompany(String mail) {
        Session session = conf.getSession();
        Company company = null;
        try {
            User user = session.load(User.class, mail);
            company = new Company(user);
            if (user.getSelectedCompany() == null)
                user.setSelectedCompany(company);
            session.save(company);
            session.update(user);
            //session.update(owner);
            session.getTransaction().commit();


        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
            return company;
        }

    }

    /**
     * @param id - id firmy
     * @return Firma
     */
    public Company getCompany(long id) {
        Session session = conf.getSession();
        Company company = null;
        try {

            company = session.load(Company.class, id);
            session.getTransaction().commit();

        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }

        return company;

    }

    /**
     * Zaznacza firmę jako domyślną
     *
     * @param mail      - mail właściciela firmy
     * @param companyId - id wybranej firmy
     */
    public void selectCompany(String mail, long companyId) {
        Session session = conf.getSession();
        try {
            User u = session.load(User.class, mail);
            Company c = session.load(Company.class, companyId);
            u.setSelectedCompany(c);
            session.update(u);
            session.getTransaction().commit();

        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }


    /**
     * Zwraca główną firmę użytkownika
     *
     * @param mail - mail użytkownika
     * @return - Główna firma
     */
    public Company getCurrentCompany(String mail) {
        Session session = conf.getSession();
        Company c;
        try {
            User u = session.load(User.class, mail);
            c = u.getSelectedCompany();


        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }

        return c;
    }


    public Company getCompanyById(long id) {
        Session session = conf.getSession();
        Company c;
        try {
            c = session.get(Company.class, id);

        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }

        return c;
    }

    public void updateCompany(Company obj) {
        Session session = conf.getSession();

        try {
            //User user = session.load(User.class, owner.getMail());
            Company company = session.load(Company.class, obj.getId());
            company.merge(obj);

            session.update(company);
            session.getTransaction().commit();

            Gson gson = new Gson();
            JsonObject params = new JsonObject();
            params.addProperty("name", company.getName());
            notificationService.createNotification("BOT", "notification.company.created", gson.toJson(params), new ArrayList<User>(Arrays.asList(company.getOwner())));
            //notificationService.createNotification("BOT","Company <b>" + company.getName() + "</b> has been created2.",new ArrayList<User>(Arrays.asList(company.getOwner())));
        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();


        }

    }


    public List<Company> getUserCompanies(String mail) throws NotFoundException {
        Session session = conf.getSession();
        List<Company> companies = null;
        _logger.info("Pobieranie firm użytkownika: " + mail);

        try {
            Query query = session.createQuery("from Company where owner=\'" + mail + "\'");
            companies = query.list();
            session.getTransaction().commit();
            _logger.info("Pobierano firmy użytkownika: " + mail + "\n" + companies);
        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
            if (companies == null) {
                throw new NotFoundException("User has no company");
            } else {
                return companies;
            }
        }


    }

    public List<User> getCompanyEmployees(String mail) throws NotFoundException {
        Session session = conf.getSession();
        List<User> employees = null;

        JSONArray json = new JSONArray();
        try {
            User user = session.load(User.class, mail);
            _logger.info("Pobieranie pracowników firmy: " + user.getSelectedCompany().getName());

            //Query query = session.createQuery("select user, i from Institution i join i.employee as user");
            Query query = session.createQuery("select u from User u join Institution i on u.institution.id = i.id where i.company.id = " + user.getSelectedCompany().getId());
            employees = query.list();


//            for (Object[] row : employees) {
//                JSONObject obj = new JSONObject();
//                User u = (User) row[0];
//                Institution i = (Institution) row[1];
//
//                obj.put("mail", u.getMail());
//                obj.put("institution", i.getName());
//                obj.put("institutionId", i.getId());
//                json.put(obj);
//            }

            session.getTransaction().commit();
            _logger.info("Pobrano pracowników firmy: " + employees);
        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
            if (employees == null) {
                throw new NotFoundException("Company has no employees");
            } else {
                //return json.toList();
                return employees;
            }
        }
    }

}
