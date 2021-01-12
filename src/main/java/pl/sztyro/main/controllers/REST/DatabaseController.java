package pl.sztyro.main.controllers.REST;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.sztyro.main.exceptions.NotFoundException;
import pl.sztyro.main.model.Company;
import pl.sztyro.main.model.Database;
import pl.sztyro.main.model.User;
import pl.sztyro.main.services.AuthService;
import pl.sztyro.main.services.CompanyService;
import pl.sztyro.main.services.DatabaseService;
import pl.sztyro.main.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;

@RestController()
@RequestMapping("api/database")
public class DatabaseController {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

    @Autowired
    DatabaseService databaseService;

    @GetMapping("/table")
    public Object getTables(HttpServletRequest request, @RequestParam Long id, @RequestParam(required = false) String tableName) throws NotFoundException, SQLException {

        User user = userService.getUser(authService.getLoggedUserMail(request));
        Company company = user.getSelectedCompany();
        Database database = databaseService.getCompanyDatabase(company);

        if (tableName != null)
            return databaseService.getTableDetails(database, tableName);
        else
            return databaseService.getTables(database);

    }

    @PostMapping("/table")
    public void insert(HttpServletRequest request, @RequestBody Object body, @RequestParam Long id, String tableName) throws NotFoundException, SQLException, ParseException {

        Gson gson = new Gson();
        JSONArray obj = new JSONArray(gson.toJson(body));

        User user = userService.getUser(authService.getLoggedUserMail(request));
        Company company = user.getSelectedCompany();
        Database database = databaseService.getCompanyDatabase(company);

        databaseService.insertRow(database, tableName, obj);


    }

    @GetMapping("/content")
    public Object getTableContent(HttpServletRequest request, @RequestParam Long id, @RequestParam String tableName) throws NotFoundException, SQLException {

        User user = userService.getUser(authService.getLoggedUserMail(request));
        Company company = user.getSelectedCompany();
        Database database = databaseService.getCompanyDatabase(company);

        return databaseService.getTableContent(database, tableName);

    }

    @GetMapping("/list")
    public Object getDatabases(HttpServletRequest request) throws NotFoundException {
        User user = userService.getUser(authService.getLoggedUserMail(request));

        Company company = user.getSelectedCompany();

        return companyService.getCompanyDatabase(company);
    }

    @PostMapping()
    public void addCompanyDatabase(HttpServletRequest request, @RequestBody Object body) throws NotFoundException {

        Gson gson = new Gson();
        JSONObject object = new JSONObject(gson.toJson(body));

        User user = userService.getUser(authService.getLoggedUserMail(request));
        Company company = user.getSelectedCompany();
        if (company.getDatabase().size() == 0)
            databaseService.addCompanyDatabase(company, object);

    }

}
