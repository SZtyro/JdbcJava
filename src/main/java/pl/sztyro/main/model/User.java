package pl.sztyro.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;


@Entity
//
//@Proxy(lazy = false)
public class User {

    /**
     * Email użytkownika
     */
    @Id
    @Column(name = "user_mail")
    private String mail;

    @JoinColumn(name = "user_selected_company")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("company_owner")
    @JsonIgnore
    private Company selectedCompany;

//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_database")
//    @JsonIgnore
//    private Database database;

    @Column(name = "user_created")
    private Date created;

    @Column(name = "user_firstname")
    private String firstname;

    @Column(name = "user_surname")
    private String surname;

    @ManyToOne()
    @JoinColumn(name = "user_institution")
    Institution institution;


//    public Database getDatabase() {
//        return database;
//    }
//
//    public void setUserDatabase(Database database) {
//        this.database = database;
//    }

    public User() {
    }

    public User(String mail) {
        this.mail = mail;
        this.created = new Date();
    }

    public void merge(User newData) {
        setMail(newData.getMail());


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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String userFirstname) {
        this.firstname = userFirstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String userSurname) {
        this.surname = userSurname;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }
}


