package pl.sztyro.main.services;

import org.apache.http.HttpRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import pl.sztyro.main.model.User;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Service
public class AuthService {

    public String getLoggedUserMail(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        Map<String, String> details = (Map<String, String>) ((OAuth2Authentication) principal).getUserAuthentication().getDetails();
        return details.get("email");
    }
}
