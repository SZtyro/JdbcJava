package pl.sztyro.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
//@Proxy(lazy = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column()
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn()
    @JsonIgnore
    private Company company;

    @ManyToMany
    @JsonIgnore
    private List<User> employees;

    public Institution(Company company) {
        this.company = company;
    }

    public Institution() {

    }

    public void merge(Institution newInstitution) {
        if (newInstitution.getName() != null)
            setName(newInstitution.getName());
        if (newInstitution.getCompany() != null)
            setCompany(newInstitution.getCompany());
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<User> getEmployees() {
        return employees;
    }

    public void setEmployees(List<User> employees) {
        this.employees = employees;
    }

    public void addEmployee(User user) {
        this.employees.add(user);
    }
}
