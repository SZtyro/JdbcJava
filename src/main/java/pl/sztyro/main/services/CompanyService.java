package pl.sztyro.main.services;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sztyro.main.controllers.HibernateConf;
import pl.sztyro.main.model.Company;
import pl.sztyro.main.model.User;

import javax.validation.constraints.NotNull;

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
     * @param mail - mail właściciela firmy
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


}
