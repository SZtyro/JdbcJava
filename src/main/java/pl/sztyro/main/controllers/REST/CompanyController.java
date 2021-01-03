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
import javax.validation.constraints.NotNull;
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
        Company obj = gson.fromJson(gson.toJson(companyJson), Company.class);
        _logger.info("Tworzenie firmy: " + obj.getName());
        try {
            //companyService.createCompany(owner, obj.get("name").getAsString(), obj.get("nip").getAsLong());
            companyService.updateCompany(obj);
            if (owner.getSelectedCompany() == null) {
                userService.selectCompany(owner.getMail(), obj.getId());
                //List<Company> company = owner.getCompanies();
                //companyService.selectCompany(owner.getMail(), companyService.getCompany(company.get(0).getId()).getId());
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
        String mail = authService.getLoggedUserMail(request);
        Company company = companyService.getCurrentCompany(mail);

        if (company == null)
            return companyService.createCompany(mail);
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

    @PutMapping("/current")
    public Company setSelectedCompany(HttpServletRequest request, @NotNull @RequestParam Long id) {

        try {
            return userService.selectCompany(authService.getLoggedUserMail(request), id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }

    }


}
