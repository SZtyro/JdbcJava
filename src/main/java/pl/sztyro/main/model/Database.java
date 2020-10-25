package pl.sztyro.main.model;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Proxy(lazy = false)
@Table(name = "UserDatabase")
public class Database {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "database_id")
    private int databaseId;
    @Column(name = "database_host")
    private String url;
    @Column(name = "database_port")
    private String port;
    @Column(name = "database_name")
    private String database;
    @Column(name = "database_login")
    private String login;
    @Column(name = "database_password")
    private String password;

    public Database(){}

    public Database(String url, String port, String database, String login, String password) {
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

    public Database(int id, String url, String port, String database, String login, String password) {
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
