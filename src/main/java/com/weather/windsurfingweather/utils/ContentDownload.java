package com.weather.windsurfingweather.utils;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
@Slf4j
//ta klasa powinna byc w pakiecie client i nazwa powinna odpowiadac temu co robi czyli WeatherClient czy cos w tym stylu
public class ContentDownload {

    public static JSONArray getDataFromUrl(String locationUrl) throws IOException {
        JSONObject jsonFromUrl = getJSONFromUrl(locationUrl);
        return jsonFromUrl.getJSONArray("data");
    }

    public static JSONObject getObjectFromUrl(String locationUrl) throws IOException {
        JSONObject jsonFromUrl = getJSONFromUrl(locationUrl);
        log.info("json object {}", jsonFromUrl);
        return jsonFromUrl;
    }

    private static JSONObject getJSONFromUrl(String url) throws IOException {

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

    private ContentDownload() {
    }
}
