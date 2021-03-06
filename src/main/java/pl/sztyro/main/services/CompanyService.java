package pl.sztyro.main.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sztyro.main.config.HibernateConf;
import pl.sztyro.main.exceptions.NotFoundException;
import pl.sztyro.main.model.Company;
import pl.sztyro.main.model.Database;
import pl.sztyro.main.model.Institution;
import pl.sztyro.main.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private static final Logger _logger = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    HibernateConf conf;

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserService userService;

    @Autowired
    InstitutionService institutionService;

    public void createCompany(User user, Company company) {
        Session session = conf.getSession();

        try {

            //Company company = new Company(user, name, nip);
            if (user.getSelectedCompany() == null)
                user.setSelectedCompany(company);

            company.setOwner(user);
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

            //Hibernate.initialize(company.getName());

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

    public void selectCompany(User user, long companyId) {
        Session session = conf.getSession();
        try {

            Company c = session.load(Company.class, companyId);
            user.setSelectedCompany(c);
            session.clear();
            session.update(user);
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
            User u = userService.getUserByMail(mail);
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

    public void updateCompany(Company obj, User user) {
        Session session = conf.getSession();

        try {
            Company company;
            if (obj.getId() == 0) {
                company = new Company();
                company.setOwner(user);
            } else
                company = session.load(Company.class, obj.getId());

            company.merge(obj);

            session.saveOrUpdate(company);
            session.getTransaction().commit();

            Gson gson = new Gson();
            JsonObject params = new JsonObject();
            params.addProperty("name", company.getName());
            notificationService.createNotification("BOT", "notification.company.updated", gson.toJson(params), new ArrayList<User>(Arrays.asList(company.getOwner())));
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
            User owner = userService.getUserByMail(mail);
            List<Institution> institutions = institutionService.getUserInstitution(mail);

            Query query = session.createQuery("from Company where owner=" + owner.getId());
            companies = query.list();


            for(Institution institution : institutions){
                companies.add(institution.getCompany());
            }

            session.getTransaction().commit();
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

    public Object getCompanyEmployees(String mail) throws NotFoundException {
        Session session = conf.getSession();
        List<User> employees = null;
        JSONArray array = new JSONArray();
        try {
            User user = userService.getUserByMail(mail);


            _logger.info("Pobieranie pracowników firmy: " + user.getSelectedCompany().getName());


            Query query = session.createQuery("from Institution i join i.employees where i.company.id=" + user.getSelectedCompany().getId());
            List<Object[]> li = query.list();


            for (Object[] obj : li) {
                JSONObject jsonObject = new JSONObject();

                User u = (User) obj[1];
                Institution i = (Institution) obj[0];

                jsonObject.put("mail", u.getMail());
                jsonObject.put("id", u.getId());
                jsonObject.put("firstname", u.getFirstname());
                jsonObject.put("surname", u.getSurname());

                jsonObject.put("name", i.getName());


                array.put(jsonObject);

            }


            session.getTransaction().commit();
            _logger.info("Pobrano pracowników firmy: " + employees);
        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
            return array.toString();
        }
    }

    public List<Database> getCompanyDatabase(Company company) {
        Session session = conf.getSession();
        List<Database> databases = null;


        try {

            Company c = session.load(Company.class, company.getId());
            _logger.info("Pobieranie baz firmy: " + company.getName());

            Hibernate.initialize(c.getDatabase());
            databases = c.getDatabase();
            session.getTransaction().commit();

        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();

            return databases;
        }

    }

    public Optional<Database> getCompanyDatabaseByName(Company company, String name) {
        Session session = conf.getSession();
        List<Database> databases = null;
        Optional<Database> answer = null;

        try {

            Company c = session.load(Company.class, company.getId());
            _logger.info("Pobieranie bazy firmy: " + c.getName() + " o nazwie: " + name);

            Hibernate.initialize(c.getDatabase());
            databases = c.getDatabase();
            answer = databases.stream()
                    .filter(db -> name.equals(db.getDatabase()))
                    .findAny();

            session.getTransaction().commit();

        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();

            return answer;
        }

    }

}
