package pl.sztyro.main.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Date;

@Component
public class GoogleService {

    public String verifyToken(String token) {

        try {
            final NetHttpTransport transport;
            transport = GoogleNetHttpTransport.newTrustedTransport();
            final JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Arrays.asList("36592518046-43kubsqj6gut5165dugs9u0cha4e0hah.apps.googleusercontent.com",
                            "36592518046-t5s49f4c057do8ru7evf35dmb9tg1ur8.apps.googleusercontent.com"))
                    .build();
            GoogleIdToken idToken = verifier.verify(token);
            //System.out.println("weryfikacja: " + idToken.verify(verifier));
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Print user identifier
                String userId = payload.getSubject();
                //System.out.println("User ID: " + userId);
                long issued = (long) payload.get("iat");
                long expiration = (long) payload.get("exp");


                /*System.out.println("Aktualna data: " + new Date().getTime()/1000);
                System.out.println("issued: " + issued);
                System.out.println("nadanie: " + new Date(issued * 1000));
                System.out.println("zostalo: " + (new Date((expiration * 1000)-(new Date().getTime() )).getTime())  );
                System.out.println("Wygasa: " + new Date((expiration)*1000) );*/
                // Get profile information from payload
                String email = payload.getEmail();
                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                String locale = (String) payload.get("locale");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");

                if((new Date((expiration * 1000)-(new Date().getTime() )).getTime()) > 0)
                    return email;

            } else {
                System.out.println("Invalid ID token.");
                return null;
            }
        } catch (GeneralSecurityException sec) {
            System.out.println("GeneralSecurityException: " + sec);
            return null;
        } catch (IOException io) {
            System.out.println("IOException: " + io);
            return null;
        }

        return null;
    }

}
