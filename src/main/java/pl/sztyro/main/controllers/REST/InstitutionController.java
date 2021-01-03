package pl.sztyro.main.controllers.REST;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.sztyro.main.exceptions.NoPermissionException;
import pl.sztyro.main.exceptions.NotFoundException;
import pl.sztyro.main.model.Institution;
import pl.sztyro.main.model.User;
import pl.sztyro.main.services.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("api/institution")
public class InstitutionController {

    private static final Logger _logger = LoggerFactory.getLogger(InstitutionController.class);

    @Autowired
    InstitutionService institutionService;

    @Autowired
    AuthService authService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

    @GetMapping()
    public Object getInstitution(@RequestParam(value = "id", required = false) Long id, HttpServletRequest request) throws NotFoundException {

        _logger.info("id: " + id);
        if (id == null) {
            return institutionService.getInstitutions(userService.getUser(authService.getLoggedUserMail(request)).getSelectedCompany());
        }else if (id == 0) {
            return new Institution(userService.getUser(authService.getLoggedUserMail(request)).getSelectedCompany());
            //return institutionService.createInstitution(authService.getLoggedUserMail(request));
        } else {
            return institutionService.getInstitution(id);
        }
    }

//    @GetMapping("/institution")
//    public Object getInstitutions(HttpServletRequest request) {
//        try {
//            //Zalogowany użytkownik
//            User owner = userService.getUser(authService.getLoggedUserMail(request));
//            _logger.info("Pobieranie placówek użytkownika: " + owner.getMail());
//            //return owner.getSelectedCompany().getInstitution();
//            return null;
//
//        } catch (NotFoundException e) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "User not found");
//        }
//    }

    @PutMapping()
    public void updateInstitution(HttpServletRequest request, @RequestBody Object institutionJson) throws NotFoundException {
        try {
            //Zalogowany użytkownik
            String mail = authService.getLoggedUserMail(request);
            Gson gson = new Gson();
            Institution obj = gson.fromJson(gson.toJson(institutionJson), Institution.class);
            institutionService.updateInstitution(mail, obj);
            _logger.info("Dodawanie placówki użytkownika: " + mail + ", o nazwie: " + obj.getName());


        } catch (NoPermissionException e) {
            _logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }
}
