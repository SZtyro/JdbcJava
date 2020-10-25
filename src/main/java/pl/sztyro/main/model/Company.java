package pl.sztyro.main.model;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Proxy(lazy = false)
//@Table(name = "User_Company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "company_id")
    /**Id firmy*/
    private int id;

    @Column(name = "company_name")
    private String name;

    @Column(name = "company_employee")
    @OneToMany
    private List<User> employee;

    @Column(name = "company_administration")
    @OneToMany
    private List<User> administration;

    @Column(name = "company_institution")
    @OneToMany
    private List<Institution> institution;




    //Getters

    public int getCompanyId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<User> getEmployees() {
        return employee;
    }

    //Setters


    public void setCompanyId(int companyId) {
        this.id = companyId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmployees(List<User> employees) {
        this.employee = employees;
    }
}
