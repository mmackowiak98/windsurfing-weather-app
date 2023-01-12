package com.weather.windsurfingweather.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.windsurfingweather.model.Date;
import com.weather.windsurfingweather.model.Url;
import com.weather.windsurfingweather.model.entity.Location;
import com.weather.windsurfingweather.model.record.GeographicalCoordinatesRecord;
import com.weather.windsurfingweather.model.record.LocationRecord;
import com.weather.windsurfingweather.service.WeatherServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WeatherController.class)
public class WeatherControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherServiceImpl weatherService;

    @Test
    public void testGetBestLocation_validStatusCode() throws Exception {
        Date date = new Date();
        LocationRecord expectedLocation = new LocationRecord("name1",15L,15L,new GeographicalCoordinatesRecord("1.0","2.0"));
        when(weatherService.getLocation(date.getDate())).thenReturn(expectedLocation);

        mockMvc.perform(get("/api/weather/best")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(date)))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddNewLocation_validStatusCode() throws Exception {
        Url url = new Url();
        url.setUrl("https://www.example.com");
        Location expectedLocation = new Location();
        when(weatherService.addNewLocation(url.getUrl())).thenReturn(expectedLocation);

        mockMvc.perform(post("/api/weather")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(url)))
                .andExpect(status().isCreated());

    }

    @Test
    public void testGetAllLocations_validStatusCode() throws Exception {
        List<Location> expectedLocations = Arrays.asList(new Location(), new Location());
        when(weatherService.getAll()).thenReturn(expectedLocations);

        mockMvc.perform(get("/api/weather/all"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testGetBestLocationWithInvalidDate() throws Exception {
        mockMvc.perform(get("/api/weather/best")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"date\":\"invalid-date\"}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testGetAllLocationsWithEmptyList() throws Exception {
        when(weatherService.getAll()).thenReturn(Collections.emptyList());
        MvcResult mvcResult = mockMvc.perform(get("/api/weather/all"))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("[]",contentAsString);
    }

    private String toJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}