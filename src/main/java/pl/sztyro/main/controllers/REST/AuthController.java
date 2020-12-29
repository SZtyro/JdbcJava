package pl.sztyro.main.controllers.REST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.sztyro.main.exceptions.NotFoundException;
import pl.sztyro.main.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@RequestMapping(path = "api")
@RestController
public class AuthController {

    private static final Logger _logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    UserService userService;

    @GetMapping("/google/user")
    /**Pobiera zalogowanego użytkownika*/
    public Object getCurrentUser(HttpServletRequest request, HttpServletResponse response) {


        Principal principal = request.getUserPrincipal();

        if (principal == null) {
            //response.sendRedirect("/api/google/auth");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
            //return null;
        } else {
            Map<String, String> details = (Map<String, String>) ((OAuth2Authentication) principal).getUserAuthentication().getDetails();
            _logger.info("Pobieranie aktualnego użytkownika: " + principal.getName());

            try {
                userService.getUser(details.get("email"));
            } catch (NotFoundException e) {
                userService.addUser(details.get("email"));
            }

            return details;
        }
    }

    /**
     * Odwołanie do tej ścieżki powoduje wysłanie formularza logowania dla niezalogowanych u zytkowników
     */
    @CrossOrigin("https://accounts.google.com")
    @GetMapping("/google/auth")
    public void googleLogin(HttpServletResponse response) throws IOException {
        _logger.info("Autoryzacja");
        response.sendRedirect("/home");
    }

}
