package pl.sztyro.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Proxy(lazy = false)
@Table(name = "UserDatabase")
public class Database {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "database_id")
    private long id;
    @Column(name = "database_host")
    private String url;
    @Column(name = "database_port")
    private String port;
    @Column(name = "database_name")
    private String database;
    @Column(name = "database_login")
    private String login;
    @Column(name = "database_password")
    @JsonIgnore
    private String password;

    public Database() {
    }

    public Database(String url, String port, String database, String login, String password) {
        this.url = url;
        this.port = port;
        this.database = database;
        this.login = login;
        this.password = password;
    }

    public Database(String url, String port, String database, String login) {
        this.url = url;
        this.port = port;
        this.database = database;
        this.login = login;
    }

    @Override
    public String toString() {
        return "UserDatabase{" +
                "databaseId=" + id +
                ", url='" + url + '\'' +
                ", port='" + port + '\'' +
                ", database='" + database + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Database(long id, String url, String port, String database, String login, String password) {
        this.id = id;
        this.url = url;
        this.port = port;
        this.database = database;
        this.login = login;
        this.password = password;
    }

    public void setDatabaseId(int databaseId) {
        databaseId = databaseId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getDatabaseId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }
}
