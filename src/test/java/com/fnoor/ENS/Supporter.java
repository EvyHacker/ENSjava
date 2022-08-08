package com.fnoor.ENS;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.fnoor.PageFields.*;
import static com.fnoor.PageFields.ens_auth_token;

public class Supporter {

    static WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public static void ensAuthTest() {
        HttpPost post = new HttpPost(SERVICE_URL + "/authenticate");
        post.setHeader("Content-Type", "application/json");
        try {

            // Test Account 09
            String body = "6c9a345f-f836-4981-b6c0-08ffed29d491";

            InputStream is = new ByteArrayInputStream(body.getBytes());
            InputStreamEntity inputStreamEntity;
            inputStreamEntity = new InputStreamEntity(is, body.getBytes().length);
            post.setEntity(inputStreamEntity);

            HttpResponse response = HttpClientBuilder.create().build().execute(post);
            int status = response.getStatusLine().getStatusCode();
            if (status != HTTP_STATUS_OK) {
                throw new IOException("Unable to authenticate. Received invalid http status=" + status);
            }

            String jsonResponse = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
            System.out.println("RESPONSE as String: " + jsonResponse);

            // use jackson library to pull the string into json objects
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(jsonResponse);
            ens_auth_token = node.get("ens-auth-token").asText();
            System.out.println("ens-auth-token: " + ens_auth_token);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
