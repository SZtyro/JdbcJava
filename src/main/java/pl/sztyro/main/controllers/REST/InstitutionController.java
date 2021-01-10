package pl.sztyro.main.controllers.REST;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.sztyro.main.exceptions.NoPermissionException;
import pl.sztyro.main.exceptions.NotFoundException;
import pl.sztyro.main.model.Institution;
import pl.sztyro.main.model.User;
import pl.sztyro.main.services.*;

import javax.servlet.http.HttpServletRequest;

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
        } else if (id == 0) {
            return new Institution(userService.getUser(authService.getLoggedUserMail(request)).getSelectedCompany());
            //return institutionService.createInstitution(authService.getLoggedUserMail(request));
        } else {
            return institutionService.getInstitution(id);
        }
    }

    @PutMapping()
    public void updateInstitution(HttpServletRequest request, @RequestBody Object institutionJson) throws NotFoundException {
        try {
            //Zalogowany użytkownik
            String mail = authService.getLoggedUserMail(request);
            Gson gson = new Gson();
            Institution obj = gson.fromJson(gson.toJson(institutionJson), Institution.class);
            institutionService.updateInstitution(mail, obj);
            _logger.info("Aktualizacja placówki użytkownika: " + mail + ", o nazwie: " + obj.getName());


        } catch (NoPermissionException e) {
            _logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @DeleteMapping()
    public void deleteInstitution(HttpServletRequest request, @RequestParam Long id) throws NotFoundException, NoPermissionException {

        String mail = authService.getLoggedUserMail(request);
        User user = userService.getUser(mail);

        Institution institution = institutionService.getInstitution(id);

        //if (!institution.getCompany().getOwner().getMail().equals(user.getMail()))
        //    throw new NoPermissionException("Only owner can delete institution.");
        institutionService.deleteInstitution(id);
    }

    @GetMapping("/employee")
    public Object getInstitutionEmployee(HttpServletRequest request) throws NotFoundException {
        String mail = authService.getLoggedUserMail(request);

        return companyService.getCompanyEmployees(mail);

    }
}
