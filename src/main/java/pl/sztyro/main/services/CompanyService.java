package pl.sztyro.main.services;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sztyro.main.controllers.HibernateConf;
import pl.sztyro.main.exceptions.NotFoundException;
import pl.sztyro.main.model.Company;
import pl.sztyro.main.model.Institution;
import pl.sztyro.main.model.User;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class CompanyService {

    private static final Logger _logger = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    HibernateConf conf;

    /**
     * @param owner
     * @param name
     * @param nip
     */
    public void createCompany(@NotNull User owner, String name, long nip) {
        Session session = conf.getSession();
        try {
            Company company = new Company(owner, name, nip);
            owner.addCompany(company);
            session.save(company);
            session.update(owner);
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

        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }

    }

    public List<Institution> getInstitutions(Company company) {
        Session session = conf.getSession();
        Company c;

        try {
            //User user = session.load(User.class, owner.getMail());
            c = session.load(Company.class, company.getId());
            session.getTransaction().commit();

        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }

        return c.getInstitution();
    }

    public void addInstitution(String mail, Institution institution) throws NotFoundException {
        Session session = conf.getSession();


        try {
            User user = session.load(User.class, mail);
            Company selectedCompany = user.getSelectedCompany();
            if (selectedCompany == null) {
                throw new NotFoundException("User has no selected company");
            } else {
                institution.setCompany(selectedCompany);
                selectedCompany.addInstitution(institution);
            }
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

    public List<Company> getUserCompanies(String mail) throws NotFoundException {
        Session session = conf.getSession();
        List<Company> companies = null;

        try {
            User user = session.load(User.class, mail);
            companies = user.getCompanies();
            session.getTransaction().commit();

        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

        if (companies == null) {
            throw new NotFoundException("User has no company");
        } else {
            return companies;
        }
    }

}
