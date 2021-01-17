package pl.sztyro.main.services;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sztyro.main.config.HibernateConf;
import pl.sztyro.main.enums.Permission;
import pl.sztyro.main.model.Company;

import java.util.EnumSet;

@Service
public class ModuleService {

    private static final Logger _logger = LoggerFactory.getLogger(ModuleService.class);

    @Autowired
    HibernateConf conf;

    private EnumSet<Permission> decodeModules(String modules) {
        EnumSet<Permission> permissions = EnumSet.allOf(Permission.class);
        permissions.removeIf(myVal -> !modules.contains(myVal.name()));

        return permissions;
    }

    public boolean hasAccess(Permission permission, Company company) {
        String modules = company.getModules();

        if (modules != null)
            return decodeModules(modules).contains(permission);
        else
            return false;
    }

    public void grantAccess(Permission permission, Company company) {

        Session session = conf.getSession();

        try {
            Company c = session.load(Company.class, company.getId());
            String modules = c.getModules();

            EnumSet<Permission> set = decodeModules(modules);

            if(set.isEmpty()){
                set.add(permission);
            }else{
                if(!set.contains(permission))
                    set.add(permission);
            }

        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }

    }
}
