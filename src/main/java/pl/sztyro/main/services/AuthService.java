package pl.sztyro.main.services;

import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.sztyro.main.controllers.REST.CompanyController;
import pl.sztyro.main.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.http.HTTPException;
import java.security.Principal;
import java.util.Map;

@Service
public class AuthService {

    private static final Logger _logger = LoggerFactory.getLogger(AuthService.class);

    public String getLoggedUserMail(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();

        _logger.info("Użytkownik: " + principal);

        if (principal != null) {
            Map<String, String> details = (Map<String, String>) ((OAuth2Authentication) principal).getUserAuthentication().getDetails();
            return details.get("email");
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

    }
}
