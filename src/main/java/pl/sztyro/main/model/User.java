package pl.sztyro.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @JoinColumn(name = "user_selected_company_id")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("company_owner")
    private Company selectedCompany;

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

    public void setMail(String mail) {
        this.mail = mail;
    }


    public Company getSelectedCompany() {
        return selectedCompany;
    }

    public void setSelectedCompany(Company selectedCompany) {
        this.selectedCompany = selectedCompany;
    }


    public String getMail() {
        return mail;
    }
}


