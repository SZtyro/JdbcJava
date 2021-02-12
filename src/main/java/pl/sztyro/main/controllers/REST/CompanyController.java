package pl.sztyro.main.controllers.REST;

import com.google.gson.Gson;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.sztyro.main.exceptions.NotFoundException;
import pl.sztyro.main.model.Company;
import pl.sztyro.main.model.User;
import pl.sztyro.main.services.AuthService;
import pl.sztyro.main.services.CompanyService;
import pl.sztyro.main.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
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
            companyService.createCompany(owner, obj.getName(), obj.getNip());
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
    public Object getCompanies(HttpServletRequest request, @RequestParam(required = false) Long id) throws NotFoundException {

        String mail = authService.getLoggedUserMail(request);

        if (id == null)
            try {
                List<Company> companies = companyService.getUserCompanies(mail);
                return companies;

            } catch (NotFoundException e) {
                _logger.error(e.getMessage());
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, e.getMessage());
            }
        else if (id == 0) {
            Company c = new Company();
            return c;
        } else {
            return companyService.getCompany(id);
        }
    }

    @GetMapping("/current")
    public Company getCurrentCompany(HttpServletRequest request) {
        String mail = authService.getLoggedUserMail(request);
        Company company = companyService.getCurrentCompany(mail);

        if (company == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "toasts.company.notfound");
            //return companyService.createCompany(mail);
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

            companyService.updateCompany(obj, owner);

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
    public void setSelectedCompany(HttpServletRequest request, @NotNull @RequestParam Long id) {

        try {
            userService.selectCompany(authService.getLoggedUserMail(request), id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }

    }


}
