package pl.sztyro.hibernate.entities;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;


@Entity
@Proxy(lazy = false)
public class User {

    @Id
    private String mail;
    private String pack;
    private String dashboard;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "databaseId")
    private UserDatabase database;

    public UserDatabase getDatabase() {
        return database;
    }
    public void setUserDatabase(UserDatabase database) {
        this.database = database;
    }

    public String getDashboard() {
        return dashboard;
    }


    public void setDashboard(String dashboard) {
        this.dashboard = dashboard;
    }


    public User() {
    }

    public User(String mail, String pack) {
        this.mail = mail;
        this.pack = pack;
    }

    public String getPack() {
        return pack;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


}
