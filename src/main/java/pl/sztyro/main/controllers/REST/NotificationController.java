package pl.sztyro.main.controllers.REST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.sztyro.main.exceptions.NotFoundException;
import pl.sztyro.main.services.AuthService;
import pl.sztyro.main.services.NotificationService;
import pl.sztyro.main.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@RequestMapping(path = "api/notification")
@RestController
public class NotificationController {

    private static final Logger _logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Autowired
    NotificationService notificationService;

    @GetMapping
    public Object getNotification(HttpServletRequest request, @RequestParam long id) {
        if (id == 0) {
            return notificationService.getUserNotifications(authService.getLoggedUserMail(request));
        } else {
            return "nie ma";
        }
    }


}
