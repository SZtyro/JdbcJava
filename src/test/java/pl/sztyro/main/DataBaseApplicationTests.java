package pl.sztyro.main;

import io.jsonwebtoken.lang.Assert;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.sztyro.main.model.Company;
import pl.sztyro.main.model.Database;
import pl.sztyro.main.model.User;
import pl.sztyro.main.services.CompanyService;
import pl.sztyro.main.services.DatabaseService;
import pl.sztyro.main.services.InstitutionService;
import pl.sztyro.main.services.UserService;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
//@RunWith(JUnitPlatform.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DataBaseApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private InstitutionService institutionService;

//    @Autowired
//    private static HibernateConf hibernateConf;

    String mail = "test@test.pl";
    User user;

//    @AfterClass
//    static public void c(){
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        //SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//        Session session = sessionFactory.getObject().openSession();
//        Query query = session.createQuery("DROP apptest");
//        query.executeUpdate();
//
//    }

    @Test
    public void contextLoads() throws Exception {

        mockMvc.perform(get("/api/company"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void addUser() {
        userService.addUser(mail,"Adam","Kowalski");
        user = userService.getUserByMail(mail);
        assertEquals(mail, user.getMail());
    }

    @Test
    public void createCompany() {
        user = userService.getUserByMail(mail);
        companyService.createCompany(user, new Company(user, "TestName", 1231230867L));
        Company company = companyService.getCompany(user.getSelectedCompany().getId());
        Assert.notNull(company);

        assertEquals("TestName", company.getName());
        assertEquals(mail, company.getOwner().getMail());
    }

    @Test
    public void addDatabase() throws JSONException {
        user = userService.getUserByMail(mail);
        Company company = companyService.getCompany(user.getSelectedCompany().getId());
        databaseService.addCompanyDatabase(company, new JSONObject("{\"host\": \"localhost\", \"port\": 3306, \"database\" : \"testHurtownia\",\"user\" : \"root\"}"));

        List<Database> list = databaseService.getCompanyDatabase(company);
        Assert.notNull(list);
        assertEquals("test", list.get(0).getDatabase());
    }

    @Test
    public void addRow() throws JSONException {
        user = userService.getUserByMail(mail);
        Company company = companyService.getCompany(user.getSelectedCompany().getId());
        Database database = databaseService.getCompanyDatabase(company).get(0);

        try {
            databaseService.insertRow(database, "dostawcy", new JSONArray("[{\"nullable\":false,\"dataType\":\"int\",\"autoIncrement\":true,\"name\":\"id\",\"primary\":true},{\"nullable\":false,\"dataType\":\"varchar\",\"autoIncrement\":false,\"name\":\"nazwa\",\"value\":\"Test\",\"primary\":false}]"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void inviteUser() {
        user = userService.getUserByMail(mail);
        Company company = companyService.getCompany(user.getSelectedCompany().getId());

        //institutionService.addInstitution(mail,new Institution(company));

    }

}
