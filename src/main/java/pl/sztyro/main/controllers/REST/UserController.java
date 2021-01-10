package pl.sztyro.main.controllers.REST;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.hibernate.Session;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.sztyro.main.config.HibernateConf;
import pl.sztyro.main.exceptions.NotFoundException;
import pl.sztyro.main.model.User;
import pl.sztyro.main.services.AuthService;
import pl.sztyro.main.services.InstitutionService;
import pl.sztyro.main.services.NotificationService;
import pl.sztyro.main.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping(path = "api/user")
public class UserController {

    private static final Logger _logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @Autowired
    InstitutionService institutionService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    HibernateConf conf;

    @PostMapping()
    public void inviteUser(HttpServletRequest request, @RequestBody Object body) throws NotFoundException {


        User user = userService.getUser(authService.getLoggedUserMail(request));
        Gson gson = new Gson();

        JSONObject object = new JSONObject(gson.toJson(body));

        String mail = object.getString("mail");

        JSONObject requestBody = new JSONObject(body);

        User oldUser = userService.getUser(mail);
        if (oldUser == null) {
            _logger.info("Zapraszanie użytkownika: " + mail);
            userService.addUser(mail);
            userService.selectCompany(mail, user.getSelectedCompany().getId());
            institutionService.addUserToInstitution(mail, object.getLong("institutionId"));

            Gson params = new Gson();
            JsonObject obj = new JsonObject();
            obj.addProperty("mail", object.getString("mail"));
            notificationService.createNotification("BOT", "notification.user.invite.success", params.toJson(obj), new ArrayList<User>(Arrays.asList(user)));

        }
    }

    @PutMapping()
    public void updateUser(HttpServletRequest request, @RequestBody Object body) {

        Gson gson = new Gson();
        JSONObject requestBody = new JSONObject(gson.toJson(body));

        //JSONObject user = requestBody.getJSONObject("user");
        Session session = conf.getSession();
        try {
            System.out.println(requestBody.toString(2));

            User u = session.load(User.class, requestBody.getString("mail"));
            _logger.info("Aktualizacja użytkownika: " + u.getMail());

            u.setInstitution(institutionService.getInstitution(requestBody.getJSONObject("institution").getLong("id")));
            u.setFirstname(requestBody.has("firstname") ? requestBody.getString("firstname") : null);
            u.setSurname(requestBody.has("surname") ? requestBody.getString("surname") : null);

            session.save(u);
            session.getTransaction().commit();


            Gson params = new Gson();
            JsonObject obj = new JsonObject();
            obj.addProperty("mail", u.getMail());
            notificationService.createNotification("BOT", "notification.user.update.success", params.toJson(obj), new ArrayList<User>(Arrays.asList(u)));

        } catch (Exception e) {
            _logger.error(e.getMessage());
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

    }

//    @DeleteMapping()
//    public void deleteUserFromInstitution(HttpServletRequest request, @RequestParam String mail) throws NotFoundException, NoPermissionException {
//
//        //String mail = authService.getLoggedUserMail(request);
//
//        User user = userService.getUser(mail);
//        institutionService.get
//
//        Institution institution = institutionService.getInstitution(id);
//
//        if (!institution.getCompany().getOwner().getMail().equals(user.getMail()))
//            throw new NoPermissionException("Only owner can delete institution.");
//        institutionService.deleteInstitution(mail, id);
//    }

    @GetMapping()
    public Object getUser(@RequestParam(required = false) String mail) throws NotFoundException {

        if (mail == null) {
            return new User();
        } else {

            _logger.info("Pobieranie użytkownika: " + mail);

//            Gson gson = new Gson();
//            JSONObject o = new JSONObject();
//
//            o.put("user", new JSONObject(gson.toJson(userService.getUser(mail), User.class)));
//
//            Institution in = institutionService.getUserInstitution(mail);
//            if (in != null) {
//                JSONObject ins = new JSONObject();
//                ins.put("institutionId", in.getId());
//                ins.put("name", in.getName());
//                o.put("institution", ins);
//            }
//
//
//            return o.toString();
            return userService.getUser(mail);
        }
    }
}
