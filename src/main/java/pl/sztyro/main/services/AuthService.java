package pl.sztyro.main.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Service
public class AuthService {

    private static final Logger _logger = LoggerFactory.getLogger(AuthService.class);

    public String getLoggedUserMail(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();



        if (principal != null) {
            Map<String, String> details = (Map<String, String>) ((OAuth2Authentication) principal).getUserAuthentication().getDetails();
            _logger.info("Zalogowany użytkownik: " + details.get("email"));
            return details.get("email");
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

    }
}
