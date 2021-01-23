package pl.sztyro.main.services;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
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
            System.out.println(((OAuth2Authentication) principal).getUserAuthentication().getCredentials().toString());


            _logger.info("Zalogowany użytkownik: " + details.get("email"));
            return details.get("email");
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

    }

    public String getTokenValue() {
        String details = new Gson().toJson(SecurityContextHolder.getContext().getAuthentication().getDetails());
        String token = new JSONObject(details).getString("tokenValue");
        return token;
    }

    public String getBearerToken() {
        return "Bearer " + getTokenValue();
    }
}
