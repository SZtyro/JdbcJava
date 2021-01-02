package pl.sztyro.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.JsonObject;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Proxy(lazy = false)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "author")
    private String author;

    @Column()
    private String message;

    @Column()
    private String params;

    @Column()
    private Date created = new Date();

    @ManyToMany
    @JsonIgnore
    private List<User> involved;

    public Notification() {
    }

    public Notification(String author, String message, String params, List<User> involved) {
        this.author = author;
        this.message = message;
        this.params = params;
        this.involved = involved;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getInvolved() {
        return involved;
    }

    public void setInvolved(List<User> involved) {
        this.involved = involved;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}