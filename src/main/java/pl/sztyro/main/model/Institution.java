package pl.sztyro.main.model;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

@Entity
@Proxy(lazy = false)
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    private String name;

    @OneToMany
    private List<User> employee;

    @ManyToOne
    private Company company;

    public Institution(Company company) {
        this.company = company;
    }

    public Institution() {

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

    public List<User> getEmployee() {
        return employee;
    }

    public void setEmployee(List<User> employee) {
        this.employee = employee;
    }
}
