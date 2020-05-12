package pl.sztyro.hibernate;

import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Proxy(lazy=false)
public class User {

    @Id
    private String mail;
    private String pack;

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
