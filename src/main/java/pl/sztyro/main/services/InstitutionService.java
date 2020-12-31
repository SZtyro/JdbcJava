package pl.sztyro.main.services;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sztyro.main.config.HibernateConf;
import pl.sztyro.main.exceptions.NotFoundException;
import pl.sztyro.main.model.Company;
import pl.sztyro.main.model.Institution;
import pl.sztyro.main.model.User;

import java.util.List;

@Service
public class InstitutionService {

    private static final Logger _logger = LoggerFactory.getLogger(InstitutionService.class);

    @Autowired
    HibernateConf conf;

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

            Query query = session.createQuery("from Institution where company=" + company);
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


        try {
            User user = session.load(User.class, mail);
            Company selectedCompany = user.getSelectedCompany();
            if (selectedCompany == null) {
                throw new NotFoundException("User has no selected company");
            } else {
                institution.setCompany(selectedCompany);
                //selectedCompany.addInstitution(institution);
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
}
