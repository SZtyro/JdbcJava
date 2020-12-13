package pl.sztyro.main.controllers.REST;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@RequestMapping(path = "api")
@RestController
public class Auth {


    @GetMapping("/google/user")
    public Object getCurrentUser(HttpServletRequest request, HttpServletResponse response) throws Exception {


        Principal p = request.getUserPrincipal();
        System.out.println("Pobieranie aktualnego użytkownika: " + p);
        if (p == null) {
            //response.sendRedirect("/api/google/auth");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
            //return null;
        } else
            return ((OAuth2Authentication) p).getUserAuthentication().getDetails();
    }

    /**
     * @param response
     * @throws IOException
     */
    @GetMapping("/google/auth")
    public String googleLogin(HttpServletResponse response) throws IOException {
        System.out.println("Autoryzacja użytkownika");
        response.sendRedirect("/home");
        return "ada";
    }

}
