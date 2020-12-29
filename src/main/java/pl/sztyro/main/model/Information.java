package pl.sztyro.main.model;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

@Entity
@Proxy(lazy = false)
public class Information {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "author")
    private String author;

    @Column()
    private String message;

    @OneToMany
    private List<User> involved;






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
}
