package pl.sztyro.main.controllers.REST;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.sztyro.main.exceptions.NotFoundException;
import pl.sztyro.main.model.Company;
import pl.sztyro.main.model.Institution;
import pl.sztyro.main.model.User;
import pl.sztyro.main.services.AuthService;
import pl.sztyro.main.services.CompanyService;
import pl.sztyro.main.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/company")
public class CompanyController {

    private static final Logger _logger = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    CompanyService companyService;

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;


    @PostMapping()
    public void createCompany(HttpServletRequest request, @RequestBody Object companyJson) throws Exception {


        //Zalogowany użytkownik
        User owner = userService.getUser(authService.getLoggedUserMail(request));
        Gson gson = new Gson();
        JsonObject obj = gson.fromJson(gson.toJson(companyJson), JsonObject.class);
        _logger.info("Tworzenie firmy: " + obj.get("name").getAsString());
        try {
            companyService.createCompany(owner, obj.get("name").getAsString(), obj.get("nip").getAsLong());
            if (owner.getSelectedCompany() == null) {
                List<Company> company = owner.getCompanies();
                companyService.selectCompany(owner.getMail(), companyService.getCompany(company.get(0).getId()).getId());
            }
        } catch (ConstraintViolationException e) {
            String message = "";
            for (Object s : e.getConstraintViolations().toArray()) {
                ConstraintViolationImpl a = (ConstraintViolationImpl) s;
                message += a.getMessage();
            }
            _logger.error(message);
            throw new Exception(message);
        }


    }

    @GetMapping()
    public Object getCompanies(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Company> companies = companyService.getUserCompanies(authService.getLoggedUserMail(request));
            return companies;

        } catch (NotFoundException e) {
            _logger.error(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @GetMapping("/current")
    public Company getCurrentCompany(HttpServletRequest request) {
        Company company = companyService.getCurrentCompany(authService.getLoggedUserMail(request));

        if (company == null)
            return new Company();
        else
            return company;
    }

    @PutMapping
    public void updateCompany(HttpServletRequest request, @RequestBody Object companyJson) throws Exception {

        //Zalogowany użytkownik
        User owner = userService.getUser(authService.getLoggedUserMail(request));
        Gson gson = new Gson();
        Company obj = gson.fromJson(gson.toJson(companyJson), Company.class);
        _logger.info("Aktualizacja firmy o id: " + obj.getId());
        try {
            companyService.updateCompany(obj);

        } catch (ConstraintViolationException e) {
            String message = "";
            for (Object s : e.getConstraintViolations().toArray()) {
                ConstraintViolationImpl a = (ConstraintViolationImpl) s;
                message += a.getMessage();
            }
            _logger.error(message);
            throw new Exception(message);
        }
    }


    @GetMapping("/institution")
    public Object getInstitutions(HttpServletRequest request) {
        try {
            //Zalogowany użytkownik
            User owner = userService.getUser(authService.getLoggedUserMail(request));
            _logger.info("Pobieranie placówek użytkownika: " + owner.getMail());
            return owner.getSelectedCompany().getInstitution();

        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @PostMapping("/institution")
    public void addInstitution(HttpServletRequest request, @RequestBody Object institutionJson) {
        try {
            //Zalogowany użytkownik
            String mail = authService.getLoggedUserMail(request);
            Gson gson = new Gson();
            Institution obj = gson.fromJson(gson.toJson(institutionJson), Institution.class);
            companyService.addInstitution(mail, obj);
            _logger.info("Dodawanie placówki użytkownika: " + mail + ", o nazwie: " + obj.getName());


        } catch (NotFoundException e) {
            _logger.error(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
