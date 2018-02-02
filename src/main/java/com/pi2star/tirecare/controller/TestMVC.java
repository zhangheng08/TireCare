package com.pi2star.tirecare.controller;

import com.pi2star.tirecare.entity.GPSMessage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Collections;

@Controller
@RequestMapping(path="/elastic")
public class TestMVC {

    @Value("${elasticsearch.clustername}")
    private String clustername;

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    @RequestMapping("/addEmployeeTest")
    public @ResponseBody String method(Model model) {

        RestClient restClient = null;

        String ret = "empty!";

        try {

            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "Janhoo08"));

            restClient = RestClient.builder(new HttpHost(host, port))
                    .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                        @Override
                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                            return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                        }
                    })
                    .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                        @Override
                        public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                            return requestConfigBuilder.setConnectTimeout(12000).setSocketTimeout(12000);
                        }
                    })
                    .setMaxRetryTimeoutMillis(12000)
                    .build();

            HttpEntity entity = new NStringEntity("" +
                    "{\n" +
                    "    \"first_name\" : \"payne\",\n" +
                    "    \"last_name\" :  \"zhang\",\n" +
                    "    \"age\" :        25,\n" +
                    "    \"about\" :      \"I love java\",\n" +
                    "    \"interests\": [ \"football\", \"movie\" ]" +
                    "}", ContentType.APPLICATION_JSON);

            Response resp = restClient.performRequest("PUT", "/megacorp/employee/3", Collections.<String, String>emptyMap(), entity);

            ret = EntityUtils.toString(resp.getEntity());

        } catch(Exception e) {

            e.printStackTrace();
            System.out.println(e.getMessage());

        } finally {

            try {
                if(restClient != null) restClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }

        }


        return ret;

    }

}
