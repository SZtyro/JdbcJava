package pl.sztyro.main.controllers.REST;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import pl.sztyro.main.exceptions.NotFoundException;
import pl.sztyro.main.model.Company;
import pl.sztyro.main.model.User;
import pl.sztyro.main.services.AuthService;
import pl.sztyro.main.services.ModuleService;
import pl.sztyro.main.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;


@RestController()
@RequestMapping("api/extensions")
public class ExtensionController {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @Autowired
    ModuleService moduleService;


    @GetMapping
    public Object getExtension() throws IOException {

        return resourceLoader.getResource("classpath:Extensions.json");
    }

    @GetMapping("/company")
    public Object getCompanyExtensions(HttpServletRequest request) throws NotFoundException, IOException, GeneralSecurityException {
        User user = userService.getUser(authService.getLoggedUserMail(request));
        Company company = user.getSelectedCompany();

        if (company != null)
            return moduleService.getCompanyExtensions(company).toString();
        else
            throw new NotFoundException("toasts.company.notSelected");

    }

    @PostMapping("/company")
    public void savePlan(HttpServletRequest request, @RequestBody Object array) throws NotFoundException, IOException {
        User user = userService.getUser(authService.getLoggedUserMail(request));
        Company company = user.getSelectedCompany();

        moduleService.grantAccess(new JSONArray(new Gson().toJson(array)), company);
    }
}
