package pl.sztyro.main.controllers.REST;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/invite")
    public String inviteUser(HttpServletRequest request, @RequestBody Object body) throws NotFoundException {


        User user = userService.getUser(authService.getLoggedUserMail(request));
        Gson gson = new Gson();

        JsonElement element = gson.fromJson(gson.toJson(body), JsonElement.class); //Converts the json string to JsonElement without POJO
        JsonObject jsonObj = element.getAsJsonObject(); //Converting JsonElement to JsonObject

        String mail = jsonObj.get("mail").getAsString();
        _logger.info("Zapraszanie użytkownika: " + mail);
        userService.addUser(mail);
        userService.selectCompany(mail, user.getSelectedCompany().getId());
        institutionService.addUserToInstitution(mail, jsonObj.get("institution_id").getAsLong());

        Gson params = new Gson();
        JsonObject obj = new JsonObject();
        obj.addProperty("mail", jsonObj.get("mail").getAsString());
        notificationService.createNotification("BOT", "notification.user.invite.success", params.toJson(obj), new ArrayList<User>(Arrays.asList(user)));
        return "user.invite.success";
    }
}
