package pl.sztyro.main.controllers.REST;

import com.google.api.client.util.DateTime;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sztyro.main.services.AuthService;
import pl.sztyro.main.services.HttpService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("api/calendar")
public class CalendarController {

    @Autowired
    HttpService httpService;

    @Autowired
    AuthService authService;

    @GetMapping("/list")
    public Object getCalendarList(HttpServletRequest request) throws IOException, URISyntaxException {

        return httpService.get(
                "https://www.googleapis.com/calendar/v3/users/me/calendarList",
                new Header[]{
                        new BasicHeader("Authorization", authService.getBearerToken())
                },
                null
        ).getJSONArray("items").toString();

    }

    @GetMapping("/events")
    public Object getCalendarEvents(HttpServletRequest request) throws IOException, URISyntaxException {

        String mail = authService.getLoggedUserMail(request);

        return httpService.get(
                "https://www.googleapis.com/calendar/v3/calendars/" + mail + "/events",
                new Header[]{
                        new BasicHeader("Authorization", authService.getBearerToken())
                },
                new NameValuePair[]{
                        new BasicNameValuePair("orderBy", "startTime"),
                        new BasicNameValuePair("singleEvents", "true"),
                        new BasicNameValuePair("timeMin", new DateTime(System.currentTimeMillis()).toString()),
                        new BasicNameValuePair("maxResults", "10")
                }
        ).getJSONArray("items").toString();

    }
}
