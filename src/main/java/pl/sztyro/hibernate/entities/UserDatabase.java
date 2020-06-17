package pl.sztyro.hibernate.entities;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Proxy(lazy = false)
@Table(name = "UserDatabase")
public class UserDatabase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int databaseId;
    @Column(name = "DB_url")
    private String url;
    @Column(name = "DB_port")
    private String port;
    @Column(name = "DB_database")
    private String database;
    @Column(name = "DB_login")
    private String login;
    @Column(name = "DB_password")
    private String password;

    public UserDatabase(){}

    public UserDatabase(String url, String port, String database, String login, String password) {
        this.url = url;
        this.port = port;
        this.database = database;
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDatabase{" +
                "databaseId=" + databaseId +
                ", url='" + url + '\'' +
                ", port='" + port + '\'' +
                ", database='" + database + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public UserDatabase(int id, String url, String port, String database, String login, String password) {
        this.databaseId = id;
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

    public int getDatabaseId() {
        return databaseId;
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
}
