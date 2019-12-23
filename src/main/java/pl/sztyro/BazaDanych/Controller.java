package pl.sztyro.BazaDanych;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sztyro.Entities.Employee;

import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class Controller {

    @Autowired
    private klasaBazy baza;


    //tymczasowo
    @Autowired
    private DataBaseApplication main;



    @RequestMapping("/entities")
    public List<Employee> entities(){ return main.Employees; }



}
