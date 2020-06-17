package pl.sztyro.main.services;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Service;
import pl.sztyro.hibernate.HibernateService;
import pl.sztyro.hibernate.entities.UserDatabase;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;


@Service
public class MainService {

    @Autowired
    GoogleService GService;
    @Autowired
    HibernateService hibernateService;

    public String decodePassword(String password) {
        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword("dev-env-secret");
        return textEncryptor.decrypt(password);
    }

    public Connection prepareConnection(String token) {
        String userMail = null;
        userMail = GService.verifyToken(token);

        if (userMail != null) {
            UserDatabase database = hibernateService.getUserByMail(userMail).getDatabase();
            try {
                return DriverManager.getConnection(
                        "jdbc:mysql://" + database.getUrl()
                                + ":" + database.getPort()
                                + "/" + database.getDatabase()
                                + "?user=" + database.getLogin()
                                + "&password=" + decodePassword(database.getPassword())
                                + "&autoReconnect=true"
                );


            } catch (Exception e) {
                System.out.println(e);
            }

            return null;

        } else {
            return null;
        }
    }

}
