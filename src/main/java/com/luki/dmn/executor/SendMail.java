package com.luki.dmn.executor;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class SendMail {

    public static void main2(String... args){
        try {
            sendSimpleMessage();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

    }
//
//    public static ClientResponse SendSimpleMessage() {
//        Client client = Client.create();
//        client.addFilter(new HTTPBasicAuthFilter("api", "e3dc90cf5d3bf40f264cd0b0ddc2993d-713d4f73-6671797a"));
//        WebResource webResource = client.resource("https://api.mailgun.net/v3/sandbox68475f35067e458abcb66eebb04ab368.mailgun.org/messages");
//        MultivaluedMapImpl formData = new MultivaluedMapImpl();
//        formData.add("from", "Mailgun Sandbox <postmaster@sandbox68475f35067e458abcb66eebb04ab368.mailgun.org>");
//        formData.add("to", "Lukas Horak <lukas.horak@gmail.com>");
//        formData.add("subject", "Hello Lukas Horak");
//        formData.add("text", "Congratulations Lukas Horak, you just sent an email with Mailgun!  You are truly awesome!");
//        return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
//                post(ClientResponse.class, formData);
//
//    }

    public static JsonNode sendSimpleMessage() throws UnirestException {

        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + "sandbox68475f35067e458abcb66eebb04ab368.mailgun.org" + "/messages")
                .basicAuth("api", "e3dc90cf5d3bf40f264cd0b0ddc2993d-713d4f73-6671797a")
                .field("from", "Mailgun Sandbox <postmaster@sandbox68475f35067e458abcb66eebb04ab368.mailgun.org>")
                .field("to", "Lukas Horak <lukas.horak@gmail.com>")
                .field("subject", "Hello Lukas Horak")
                .field("text", "Congratulations Lukas Horak, you just sent an email with Mailgun!  You are truly awesome!")
                .asJson();

        return request.getBody();
    }
// You can see a record of this email in your logs: https://app.mailgun.com/app/logs.

// You can send up to 300 emails/day from this sandbox server.
// Next, you should add your own domain so you can send 10000 emails/month for free.
}
