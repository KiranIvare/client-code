package com.qubitons.clientcode.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class HttpClientUtils {

    private OkHttpClient client = new OkHttpClient();

    private final Logger  log = Logger.getLogger(String.valueOf(HttpClientUtils.class));

    public String performGetOperation(String url) throws IOException {
        log.info("Sending request to " + url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public Map<String, Object> performHttpCallAndReturnMap(String url) throws IOException {
        String response = performGetOperation(url);
        return new ObjectMapper().readValue(response, HashMap.class);
    }

}