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
        Database database = databaseService.getDatabase(id);

        if (tableName != null)
            return databaseService.getTableDetails(database, tableName);
        else
            return databaseService.getTables(database);

    }

    @GetMapping("/table/reference")
    public Object getReference(HttpServletRequest request, @RequestParam Long id, @RequestParam String tableName) throws NotFoundException, SQLException {

        User user = userService.getUser(authService.getLoggedUserMail(request));
        Company company = user.getSelectedCompany();
        Database database = databaseService.getCompanyDatabase(company);

        return databaseService.getTableReferences(database, tableName);

    }

    @GetMapping("/table/columnKeys")
    public Object getForeignKeys(HttpServletRequest request, @RequestParam Long id, @RequestParam String tableName, @RequestParam String column) throws NotFoundException, SQLException {

        User user = userService.getUser(authService.getLoggedUserMail(request));
        Company company = user.getSelectedCompany();
        Database database = databaseService.getCompanyDatabase(company);

        return databaseService.getTableForeignKeys(database, tableName, column);

    }

    @PostMapping("/table/row")
    public void insert(HttpServletRequest request, @RequestBody Object body, @RequestParam Long id, String tableName) throws NotFoundException, SQLException, ParseException {

        Gson gson = new Gson();
        JSONArray obj = new JSONArray(gson.toJson(body));

        User user = userService.getUser(authService.getLoggedUserMail(request));
        Company company = user.getSelectedCompany();
        Database database = databaseService.getCompanyDatabase(company);

        databaseService.insertRow(database, tableName, obj);


    }

    @PutMapping("/table/row")
    public void update(HttpServletRequest request, @RequestBody Object body, @RequestParam Long id, @RequestParam String tableName) throws NotFoundException, SQLException, ParseException {

        Gson gson = new Gson();
        JSONArray obj = new JSONArray(gson.toJson(body));

        User user = userService.getUser(authService.getLoggedUserMail(request));
        Company company = user.getSelectedCompany();
        Database database = databaseService.getCompanyDatabase(company);

        databaseService.updateRow(database, tableName, obj);


    }

    @DeleteMapping("/table/row")
    public void deleteRow(HttpServletRequest request, @RequestParam Long id, @RequestParam String tableName, @RequestParam String columnName, @RequestParam Object rowId) throws NotFoundException, SQLException, ParseException {

        User user = userService.getUser(authService.getLoggedUserMail(request));
        Company company = user.getSelectedCompany();
        Database database = databaseService.getDatabase(id);

        databaseService.deleteRow(database, tableName, columnName, rowId);
    }

    @GetMapping("/content")
    public Object getTableContent(HttpServletRequest request, @RequestParam Long id, @RequestParam String tableName) throws NotFoundException, SQLException {

        User user = userService.getUser(authService.getLoggedUserMail(request));
        Company company = user.getSelectedCompany();
        Database database = databaseService.getDatabase(id);

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

        databaseService.addCompanyDatabase(company, object);

    }

    @PutMapping()
    public void updateCompanyDatabase(HttpServletRequest request, @RequestBody Object body) throws NotFoundException {

        Gson gson = new Gson();
        JSONObject object = new JSONObject(gson.toJson(body));

        User user = userService.getUser(authService.getLoggedUserMail(request));
        Company company = user.getSelectedCompany();

        databaseService.updateCompanyDatabase(object);

    }

    @GetMapping()
    public Object getDatabase(HttpServletRequest request, @RequestParam Long id) throws NotFoundException {
        User user = userService.getUser(authService.getLoggedUserMail(request));

        if(id == 0)
            return new Database();

        Company company = companyService.getCompany(user.getSelectedCompany().getId());
        Database db = null;
        db = databaseService.getDatabase(id);
        if (db == null)
            throw new NotFoundException("toasts.database.notfound");
        else
            return db;

    }
}
