package pl.sztyro.main.controllers.REST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.sztyro.main.services.AuthService;
import pl.sztyro.main.services.NotificationService;
import pl.sztyro.main.services.UserService;

import javax.servlet.http.HttpServletRequest;

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
            return null;
        }
    }


}
