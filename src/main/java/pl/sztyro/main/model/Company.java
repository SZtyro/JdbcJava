package pl.sztyro.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.Range;
import pl.sztyro.main.exceptions.NotFoundException;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Transactional
@Proxy(lazy = false)
//@Table(name = "User_Company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column()
    /**Id firmy*/
    private long id;

    @JoinColumn()
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User owner;

    @Column()
    private String name;

    @Column(unique = true, nullable = false, length = 10)
    @Range(min = 1000000000, max = 9999999999L, message = "toasts.company.nip")
    @NotNull
    private long nip;

    @Column()
    private String address;

    @Column()
    private String city;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Database> database;

    @Column()
    @JsonIgnore
    private String modules;


    public void merge(Company company) {
        this.setName(company.getName());
        this.setNip(company.getNip());
        this.setName(company.getName());
        this.setAddress(company.getAddress());
        this.setCity(company.getCity());
    }

    public Company() {

    }

    public Company(@NotNull User owner, String name, @Range(min = 1000000000, max = 9999999999L, message = "toasts.company.nip") @NotNull long nip) {
        this.owner = owner;
        this.name = name;
        this.nip = nip;
    }

    public Company(@NotNull User owner, String name, @Range(min = 1000000000, max = 9999999999L, message = "toasts.company.nip") @NotNull long nip, String address, String city) {
        this.owner = owner;
        this.name = name;
        this.nip = nip;
        this.address = address;
        this.city = city;
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

    public String getModules() {
        return modules;
    }

    public void setModules(String modules) {
        this.modules = modules;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setId(long id) {
        this.id = id;
    }
}
