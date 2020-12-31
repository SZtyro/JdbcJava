package pl.sztyro.main.controllers.REST;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.sztyro.main.exceptions.NotFoundException;
import pl.sztyro.main.model.Institution;
import pl.sztyro.main.model.User;
import pl.sztyro.main.services.AuthService;
import pl.sztyro.main.services.CompanyService;
import pl.sztyro.main.services.InstitutionService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/institution")
public class InstitutionController {

    private static final Logger _logger = LoggerFactory.getLogger(InstitutionController.class);

    @Autowired
    InstitutionService institutionService;

    @Autowired
    AuthService authService;

    @GetMapping()
    public Institution getInstitution(@Nullable @RequestParam(value = "id") long id, HttpServletRequest request) throws NotFoundException {

        _logger.info("id: " + id);

        if (id == 0) {
            return institutionService.createInstitution(authService.getLoggedUserMail(request));
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

//    @PostMapping()
//    public void addInstitution(HttpServletRequest request, @RequestBody Object institutionJson) {
//        try {
//            //Zalogowany użytkownik
//            String mail = authService.getLoggedUserMail(request);
//            Gson gson = new Gson();
//            Institution obj = gson.fromJson(gson.toJson(institutionJson), Institution.class);
//            ins.addInstitution(mail, obj);
//            _logger.info("Dodawanie placówki użytkownika: " + mail + ", o nazwie: " + obj.getName());
//
//
//        } catch (NotFoundException e) {
//            _logger.error(e.getMessage());
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, e.getMessage());
//        }
//    }
}
