package pl.sztyro.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
//@Proxy(lazy = false)
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    private String name;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<User> employee;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private Company company;

    public Institution(Company company) {
        this.company = company;
    }

    public Institution() {

    }

    public void merge(Institution newInstitution) {
        setName(newInstitution.getName());
        setCompany(newInstitution.getCompany());
        //setEmployee(newInstitution.getEmployee());
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

//    public List<User> getEmployee() {
//        return employee;
//    }
//
//    public void setEmployee(List<User> employee) {
//        this.employee = employee;
//    }

    public long getId() {
        return id;
    }

//    public void addEmployee(User employee) {
//        this.employee.add(employee);
//    }
//
//    public void deleteEmployee(User employee){
//        this.employee.remove(employee);
//    }

    public void setId(long id) {
        this.id = id;
    }
}
