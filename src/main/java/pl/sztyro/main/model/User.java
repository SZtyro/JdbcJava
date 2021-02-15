package pl.sztyro.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;


@Entity
//
//@Proxy(lazy = false)
@Table(name = "AppUser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column()
    private long id;

    @Column(unique = true)
    private String mail;

    @JoinColumn()
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonIgnore
    private Company selectedCompany;

    @Column()
    private Date created;

    @Column()
    private String firstname;

    @Column()
    private String surname;

    public User() {
    }

    public User(String mail) {
        this.mail = mail;
        this.created = new Date();
    }

    public User(String mail, String firstname, String surname) {
        this.mail = mail;
        this.firstname = firstname;
        this.surname = surname;
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

    public long getId() {
        return id;
    }

}


