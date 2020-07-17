package com.luki.dmn.executor;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PentahoHttpClient {

    private static Logger logger = Logger.getLogger(PentahoHttpClient.class.getName());

    public static void main(String... args) throws IOException {
        PentahoHttpClient c = new PentahoHttpClient();
        String accessToken = c.receiveAccessToken();
        System.out.println(accessToken);

        String result = c.receiveData(accessToken);
        System.out.println(result);
    }



    public String receiveData(String accessToken) throws IOException {
        String url = "http://webapitest.cninfo.com.cn/api/en/p_ENS7006";

        String scode = "000659";
        String last = "1";

        return this.receiveData(accessToken, url, scode, last);
    }

    public String receiveData(String accessToken, String url, String scode, String last) throws IOException {

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("scode", scode));
        urlParameters.add(new BasicNameValuePair("last", last));
        urlParameters.add(new BasicNameValuePair("format", "json"));
        urlParameters.add(new BasicNameValuePair("access_token", accessToken));

        String result = this.callUrl(url, urlParameters);

        return result;
    }

    public String receiveAccessToken() throws IOException {
        String url = "http://webapitest.cninfo.com.cn/api-cloud-platform/oauth2/token";

        String clientId = "4db8ef431f8d41d3bbc4b66eb38125c5";
        String clientSecret = "225bd074acab4ef3a19fbadff723e722";

        String accessToken = this.receiveAccessToken(url, clientId, clientSecret);
        return accessToken;
    }

    public String receiveAccessToken(String url, String clientId, String clientSecret) throws IOException {

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("client_id", clientId));
        urlParameters.add(new BasicNameValuePair("client_secret", clientSecret));
        urlParameters.add(new BasicNameValuePair("grant_type", "client_credentials"));

        String result = this.callUrl(url, urlParameters);
        String accessToken = this.parseAccessToken(result);
        return accessToken;
    }

    public String callUrl(String url, List<NameValuePair> urlParameters) throws IOException {

        String result = "";
        HttpPost post = new HttpPost(url);

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)){
            result = EntityUtils.toString(response.getEntity());
        }
        return result;
    }


    public String parseAccessToken(String jsonResult){
        String accessToken = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonResult);
            accessToken = jsonObject.get("access_token").toString();
            System.out.println(accessToken);
            logger.info("AccessToken:" + accessToken);
        }catch (JSONException err){
            logger.warning("Not possible to Parse Json");
        }
        return accessToken;
    }


}
