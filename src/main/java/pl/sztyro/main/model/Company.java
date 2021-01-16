package pl.sztyro.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.sztyro.main.exceptions.NotFoundException;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Transactional
//@Proxy(lazy = false)
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
    private long nip;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Database> database;


    public void merge(Company company) {
        this.setName(company.getName());
        this.setNip(company.getNip());
    }

    public Company() {

    }

    public Company(@NotNull User owner) {
        this.owner = owner;
    }

    public Database getDatabaseByName(String name) throws NotFoundException {
        return database.stream()
                .filter(db -> name.equals(db.getDatabase()))
                .findAny()
                .orElseThrow(() -> new NotFoundException("toasts.database.notfound"));
    }

    public void addDatabase(Database db) {
        this.database.add(db);
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

    public List<Database> getDatabase() {
        return database;
    }

    public void setDatabase(List<Database> database) {
        this.database = database;
    }
}
