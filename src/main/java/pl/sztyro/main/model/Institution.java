package pl.sztyro.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
//@Proxy(lazy = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    private String name;

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
}
