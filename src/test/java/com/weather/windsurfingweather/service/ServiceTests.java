package com.weather.windsurfingweather.service;


import com.weather.windsurfingweather.client.WeatherClient;
import com.weather.windsurfingweather.exceptions.DateOutOfRangeException;
import com.weather.windsurfingweather.exceptions.LocationAlreadyExistsException;
import com.weather.windsurfingweather.helper.LocationHelper;
import com.weather.windsurfingweather.model.entity.Location;
import com.weather.windsurfingweather.model.record.GeographicalCoordinatesRecord;
import com.weather.windsurfingweather.model.record.LocationRecord;
import com.weather.windsurfingweather.repo.LocationRepository;
import com.weather.windsurfingweather.util.Calculations;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ServiceTests {
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private Calculations calculations;
    @Mock
    private LocationHelper locationHelper;
    @Mock
    private WeatherClient weatherClient;
    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Test
    public void testGetLocation_validInput_validOutput() throws IOException {
        LocalDate date = LocalDate.now();

        LocationRecord expectedOutput = new LocationRecord("location1", 20L, 5L, new GeographicalCoordinatesRecord("1.0", "2.0"));

        Map<String, LocationRecord> filteredLocations = new HashMap<>();

        filteredLocations.put("location1", expectedOutput);

        Map<String, Long> mapOfValuesFromFormula = new HashMap<>();

        mapOfValuesFromFormula.put("location1", 100L);

        when(calculations.filterByRequirements(any())).thenReturn(filteredLocations);
        when(calculations.calculateValueFromFormula(filteredLocations)).thenReturn(mapOfValuesFromFormula);
        when(locationRepository.findAll()).thenReturn(new ArrayList<>());

        LocationRecord actualOutput = weatherService.getLocation(date);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testGetLocation_emptyInput_nullOutput() throws IOException {

        LocalDate date = LocalDate.now();
        when(calculations.filterByRequirements(any())).thenReturn(new HashMap<>());

        LocationRecord actualOutput = weatherService.getLocation(date);
        assertNull(null, actualOutput);
    }


    @Test
    public void testAddNewLocation_validInput_validOutput() throws IOException, JSONException {
        Location expectedOutput = new Location("url1", "location1", "1.0", "2.0");
        JSONObject objectFromUrl = new JSONObject();
        objectFromUrl.put("city_name", "location1");
        objectFromUrl.put("lon", "1.0");
        objectFromUrl.put("lat", "2.0");

        when(weatherClient.getObjectFromUrl(anyString())).thenReturn(objectFromUrl);

        when(locationRepository.save(any())).thenReturn(expectedOutput);
        when(locationRepository.existsByLocationNameIgnoreCase(anyString())).thenReturn(false);
        Location actualOutput = weatherService.addNewLocation(anyString());
        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    public void testGetAll_validInput_validOutput() {
        List<Location> expectedOutput = new ArrayList<>();
        expectedOutput.add(new Location("url1", "location1", "1.0", "2.0"));
        when(locationRepository.findAll()).thenReturn(expectedOutput);

        Collection<Location> actualOutput = weatherService.getAll();
        assertEquals(expectedOutput, actualOutput);
    }
    @Test
    public void testGetAll_validInput_validOutputSize() {
        List<Location> expectedOutput = new ArrayList<>();
        expectedOutput.add(new Location("url1", "location1", "1.0", "2.0"));
        expectedOutput.add(new Location("url2", "location1", "1.0", "2.0"));
        when(locationRepository.findAll()).thenReturn(expectedOutput);

        Collection<Location> actualOutput = weatherService.getAll();
        assertEquals(2, actualOutput.size());
    }

    @Test
    public void testAddNewLocation_locationAlreadyExists_throwsException() throws JSONException, IOException {
        JSONObject objectFromUrl = new JSONObject();
        objectFromUrl.put("city_name", "location1");
        objectFromUrl.put("lon", "1.0");
        objectFromUrl.put("lat", "2.0");

        when(weatherClient.getObjectFromUrl(anyString())).thenReturn(objectFromUrl);
        when(locationRepository.existsByLocationNameIgnoreCase("location1")).thenReturn(true);

        assertThrows(LocationAlreadyExistsException.class, () -> weatherService.addNewLocation("url1"));
    }

    @Test
    public void testGetMatchingLocations_dateOutOfRange_throwsException(){
        LocalDate date = LocalDate.now().plusDays(17);
        assertThrows(DateOutOfRangeException.class, () -> weatherService.getMatchingLocations(date));
    }
}
