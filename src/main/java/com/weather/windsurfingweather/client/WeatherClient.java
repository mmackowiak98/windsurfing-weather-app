package com.weather.windsurfingweather.client;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
@Slf4j
@Component
public class WeatherClient {

    public JSONArray getDataFromUrl(String locationUrl) throws IOException {
        JSONObject jsonFromUrl = getJSONFromUrl(locationUrl);
        return jsonFromUrl.getJSONArray("data");
    }

    public JSONObject getObjectFromUrl(String locationUrl) throws IOException {
        JSONObject jsonFromUrl = getJSONFromUrl(locationUrl);
        log.info("json object {}", jsonFromUrl);
        return jsonFromUrl;
    }

    public JSONObject getJSONFromUrl(String url) throws IOException {

        URL address = new URL(url);
        HttpURLConnection con = (HttpURLConnection) address.openConnection();
        con.setRequestMethod("GET");

        JSONObject json = null;

        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            json = new JSONObject(content.toString());
            in.close();

        } else {
            throw new IOException();
        }
        log.info("json {}", json);
        return json;

    }
}
