package pl.sztyro.main.services;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class HttpService {

    public JSONObject get(String uri, Header[] headers, NameValuePair[] params) throws IOException, URISyntaxException {

        URIBuilder builder = new URIBuilder(uri);
        if (params != null)
            builder.setParameters(params);

        HttpGet httpGet = new HttpGet(builder.build());

        httpGet.setHeaders(headers);

        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(httpGet);

        JSONObject answer = new JSONObject(EntityUtils.toString(response.getEntity()));

        //System.out.println(answer.toString(2));
        return answer;
    }
}
