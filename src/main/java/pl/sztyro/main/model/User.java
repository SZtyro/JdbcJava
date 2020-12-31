package pl.sztyro.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;
import pl.sztyro.main.model.enums.UserType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
//
@Proxy(lazy = false)
public class User {

    /**
     * Email użytkownika
     */
    @Id
    @Column(name = "user_mail")
    private String mail;

    @JoinColumn(name = "user_selected_company")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Company selectedCompany;

    /**
     * Pakiety
     */
    @Column(name = "user_pack")
    private String pack;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_database")
    private Database database;

//
//    @OneToMany
//    @Fetch(FetchMode.JOIN)
//    @Column(name = "user_companies")
//    private List<Company> companies = null;

    @Column(name = "user_created")
    private Date created;

    public Database getDatabase() {
        return database;
    }

    public void setUserDatabase(Database database) {
        this.database = database;
    }

    public User() {
    }

    public User(String mail) {
        this.mail = mail;
        this.created = new Date();
    }

    public String getPack() {
        return pack;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

//    public List<Company> getCompanies() {
//        return companies;
//    }

//    public void setCompanies(List<Company> companies) {
//        this.companies = companies;
//    }

    public Company getSelectedCompany() {
        return selectedCompany;
    }

    public void setSelectedCompany(Company selectedCompany) {
        this.selectedCompany = selectedCompany;
    }
//
//    public void addCompany(Company company){
//        this.companies.add(company);
//    }

    public String getMail() {
        return mail;
    }
}


