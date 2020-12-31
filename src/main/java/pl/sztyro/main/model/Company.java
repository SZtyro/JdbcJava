package pl.sztyro.main.model;

import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

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
    private User owner;

    @Column(name = "company_name")
    @NotNull
    @NotBlank
    private String name;

    @Column(name = "company_NIP")
    @NotNull
    //@Pattern(regexp = "^\\d{10}$")
    @Range(min = 1000000000, max = 9999999999L, message = "Number not in range {min} - {max}")
    private long nip;

    @Column(name = "company_administration")
    @OneToMany
    private List<User> administration;

    @Column(name = "company_news")
    @OneToMany
    private List<Information> news;

    public void merge(Company company) {
        this.setName(company.getName());
        this.setNip(company.getNip());
    }

    public Company() {

    }

    public Company(@NotNull User owner, @NotNull String name, @NotNull long nip) {
        this.owner = owner;
        this.name = name;
        this.nip = nip;
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

}
