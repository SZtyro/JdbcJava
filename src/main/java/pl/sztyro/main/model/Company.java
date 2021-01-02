package pl.sztyro.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Proxy(lazy = false)
//@Table(name = "User_Company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "company_id")
    /**Id firmy*/
    private long id;

    @JoinColumn(name = "company_owner")
    @NotNull
    @ManyToOne
    @JsonIgnore
    private User owner;

    @Column(name = "company_name")
    private String name;

    @Column(name = "company_NIP")
    //@NotNull
    //@Pattern(regexp = "^\\d{10}$")
    //@Range(min = 1000000000, max = 9999999999L, message = "Number not in range {min} - {max}")
    private long nip;

    @Column(name = "company_news")
    @OneToMany
    private List<Notification> news;

    public void merge(Company company) {
        this.setName(company.getName());
        this.setNip(company.getNip());
    }

    public Company() {

    }

    public Company(@NotNull User owner) {
        this.owner = owner;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNip() {
        return nip;
    }

    public void setNip(long nip) {
        this.nip = nip;
    }

    public long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
