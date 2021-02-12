package pl.sztyro.main;

import io.jsonwebtoken.lang.Assert;
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
import pl.sztyro.main.model.User;
import pl.sztyro.main.services.CompanyService;
import pl.sztyro.main.services.UserService;

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
        userService.addUser(mail);
        user = userService.getUserByMail(mail);
        assertEquals(mail, user.getMail());
    }

    @Test
    public void createCompany() {
        user = userService.getUserByMail(mail);
        companyService.createCompany(user, "TestName", null);
        Company company = companyService.getCompany(user.getSelectedCompany().getId());
        Assert.notNull(company);

        assertEquals("TestName", company.getName());
        assertEquals(mail, company.getOwner().getMail());
    }



}
