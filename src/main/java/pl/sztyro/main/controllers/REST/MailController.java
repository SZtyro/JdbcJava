package pl.sztyro.main.controllers.REST;

import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.sztyro.main.services.AuthService;
import pl.sztyro.main.services.HttpService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("api/mail")
public class MailController {

    @Autowired
    HttpService httpService;

    @Autowired
    AuthService authService;

    @GetMapping()
    public Object getMails(@RequestParam String maxResults, @RequestParam(required = false) String nextPageToken) throws IOException, URISyntaxException {

        NameValuePair params[];
        if (nextPageToken != null) {
            params = new NameValuePair[]{
                    new BasicNameValuePair("maxResults", maxResults),
                    new BasicNameValuePair("pageToken", nextPageToken)
            };


        } else {
            params = new NameValuePair[]{
                    new BasicNameValuePair("maxResults", maxResults)
            };
        }


        JSONObject object = httpService.get(
                "https://gmail.googleapis.com/gmail/v1/users/me/messages",
                new Header[]{
                        new BasicHeader("Authorization", authService.getBearerToken())
                },
                params
        );

        if (object.has("messages")) {
            List<Object> li = object.getJSONArray("messages").toList();

            Gson gson = new Gson();

            JSONObject answer = new JSONObject();
            JSONArray array = new JSONArray();

            li.forEach(obj -> {
                String id = new JSONObject(gson.toJson(obj)).getString("id");

                try {
                    array.put(httpService.get(
                            "https://gmail.googleapis.com/gmail/v1/users/me/messages/" + id,
                            new Header[]{
                                    new BasicHeader("Authorization", authService.getBearerToken())
                            },
                            new NameValuePair[]{
                                    new BasicNameValuePair("format", "METADATA"),
                                    new BasicNameValuePair("metadataHeaders", "from"),
                                    new BasicNameValuePair("metadataHeaders", "subject")
                            }
                    ));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            });

            answer.put("messages", array);
            if (object.has("nextPageToken"))
                answer.put("nextPageToken", object.getString("nextPageToken"));
            answer.put("resultSizeEstimate", object.get("resultSizeEstimate"));

            JSONObject profile = httpService.get(
                    "https://gmail.googleapis.com/gmail/v1/users/me/profile",
                    new Header[]{
                            new BasicHeader("Authorization", authService.getBearerToken())
                    },
                    null
            );

            answer.put("messagesTotal", profile.get("messagesTotal"));
            return answer.toString();
        } else
            return object.toString();
    }
}
