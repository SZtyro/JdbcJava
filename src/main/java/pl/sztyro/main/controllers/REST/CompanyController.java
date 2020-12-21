package pl.sztyro.main.controllers.REST;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.sztyro.main.exceptions.NotFoundException;
import pl.sztyro.main.model.Company;
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
    public Object getCompanies(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<Company> companies = userService.getUserCompanies(authService.getLoggedUserMail(request));
            //if (companies == null || companies.isEmpty()) {

            //throw new ResponseStatusException(
            //       HttpStatus.NOT_FOUND, "User has no companies.");
            //} else {
            return companies;
            //}
        } catch (NotFoundException e) {
            e.printStackTrace();
            response.sendError(404, e.getMessage());
        }
        return null;
    }
}
