package pl.sztyro.main.services;

import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import pl.sztyro.main.config.HibernateConf;
import pl.sztyro.main.enums.Permission;
import pl.sztyro.main.model.Company;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Service
public class ModuleService {

    private static final Logger _logger = LoggerFactory.getLogger(ModuleService.class);

    @Autowired
    HibernateConf conf;

    @Autowired
    ResourceLoader resourceLoader;

    private JSONArray getExtensions() throws IOException {
        return new JSONArray(IOUtils.toString(resourceLoader.getResource("classpath:Extensions.json").getURI(), StandardCharsets.UTF_8));
    }

    public EnumSet<Permission> decodeModules(String modules) {
        EnumSet<Permission> permissions = EnumSet.allOf(Permission.class);
        if (modules != null) {
            permissions.removeIf(myVal -> !modules.contains(myVal.name()));

            return permissions;
        } else
            return null;
    }

    public boolean hasAccess(Permission permission, Company company) {
        String modules = company.getModules();

        if (modules != null)
            return decodeModules(modules).contains(permission);
        else
            return false;
    }

    public void grantAccess(JSONArray permission, Company company) throws IOException {
        _logger.info("Aktualizacja rozszerzeń firmy: " + company.getName());


        Session session = conf.getSession();

        try {
            Company c = session.load(Company.class, company.getId());
            String modules = c.getModules();

            EnumSet<Permission> set = decodeModules(modules);

            List<Permission> li = new ArrayList<>();
            permission.forEach(o -> {
                JSONObject jsonObject = new JSONObject(o.toString());
                li.add(Permission.valueOf(jsonObject.getString("permission")));

            });
            c.setModules(li.toString());

            session.update(c);
            session.getTransaction().commit();

        } catch (Exception e) {
            _logger.error(e.getMessage());
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }

    }

    public JSONArray getCompanyExtensions(Company company) throws IOException {

        Session session = conf.getSession();

        Company c = session.load(Company.class, company.getId());
        EnumSet<Permission> companyPermissions = decodeModules(c.getModules());


        if (companyPermissions != null) {
            JSONArray array = getExtensions();


            JSONArray answer = new JSONArray();


            array.forEach(o -> {
                JSONObject jsonObject = new JSONObject(o.toString());
                if (companyPermissions.contains(Permission.valueOf(jsonObject.getString("permission")))) {
                    answer.put(jsonObject);
                }
            });
            session.close();
            return answer;
        } else {
            session.close();
            return new JSONArray();
        }


    }

}
