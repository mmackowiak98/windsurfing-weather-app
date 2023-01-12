package com.weather.windsurfingweather.helper;

import com.weather.windsurfingweather.client.WeatherClient;
import com.weather.windsurfingweather.exceptions.NoForecastFoundException;
import com.weather.windsurfingweather.model.entity.Location;
import com.weather.windsurfingweather.model.record.GeographicalCoordinatesRecord;
import com.weather.windsurfingweather.model.record.LocationRecord;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class HelperTests {

    @Mock
    private WeatherClient weatherClient;

    @InjectMocks
    private LocationHelper locationHelper;



    @Test
    public void testFilterMatchingLocations_validInput_validOutput() throws IOException, JSONException {

        LocalDate date = LocalDate.now();
        Collection<Location> allLocations = new ArrayList<>();
        allLocations.add(new Location("url1", "location1", "1.0", "2.0"));

        JSONArray dataFromUrl1 = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("datetime", date.toString());
        jsonObject1.put("temp", 20);
        jsonObject1.put("wind_spd", 5);
        dataFromUrl1.put(jsonObject1);

        when(weatherClient.getDataFromUrl("url1")).thenReturn(dataFromUrl1);


        Map<String, LocationRecord> actualOutput = locationHelper.filterMatchingLocations(date, allLocations);
        Map<String, LocationRecord> expectedOutput = new HashMap<>();
        expectedOutput.put("location1", new LocationRecord("location1", 20L, 5L, new GeographicalCoordinatesRecord("1.0", "2.0")));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFilterMatchingLocations_noForecastFound_throwsException() throws IOException, JSONException {
        LocalDate date = LocalDate.now();
        Collection<Location> allLocations = new ArrayList<>();
        allLocations.add(new Location("url1", "location1", "1.0", "2.0"));

        JSONArray dataFromUrl1 = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("datetime", "invalid_date");
        jsonObject1.put("temp", 20);
        jsonObject1.put("wind_spd", 5);
        dataFromUrl1.put(jsonObject1);

        when(weatherClient.getDataFromUrl("url1")).thenReturn(dataFromUrl1);

        assertThrows(NoForecastFoundException.class, () ->  locationHelper.filterMatchingLocations(date, allLocations));
    }

}
