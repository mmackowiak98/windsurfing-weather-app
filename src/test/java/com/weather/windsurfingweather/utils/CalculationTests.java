package com.weather.windsurfingweather.utils;

import com.weather.windsurfingweather.model.record.GeographicalCoordinatesRecord;
import com.weather.windsurfingweather.model.record.LocationRecord;
import com.weather.windsurfingweather.util.Calculations;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CalculationTests {

    @Test
    void testFilterByRequirements_validInput_validOutputSize() {
        Map<String, LocationRecord> testLocations = new HashMap<>();
        testLocations.put("Location1", new LocationRecord("name1",10L,15L,new GeographicalCoordinatesRecord("0","0")));

        Calculations calculations = new Calculations(5,18,5,35);

        Map<String, LocationRecord> filteredLocations = calculations.filterByRequirements(testLocations);
        assertEquals(1, filteredLocations.size());
        assertTrue(filteredLocations.containsKey("Location1"));
    }

    @Test
    void testFilterByRequirements_invalidInput_validOutputSize() {
        Map<String, LocationRecord> testLocations = new HashMap<>();
        testLocations.put("Location1", new LocationRecord("name1",3L,15L,new GeographicalCoordinatesRecord("0","0")));

        Calculations calculations = new Calculations(5,18,5,35);

        Map<String, LocationRecord> filteredLocations = calculations.filterByRequirements(testLocations);
        assertEquals(0, filteredLocations.size());
        assertFalse(filteredLocations.containsKey("Location1"));
    }

    @Test
    void testFilterByRequirements_nullInput_validOutputEmptyMap() {

        Calculations calculations = new Calculations(5,18,5,35);
        Map<String, LocationRecord> actualOutput = calculations.filterByRequirements(null);
        assertEquals(Collections.emptyMap(),actualOutput);
    }

    @Test
    void testFilterByRequirements_emptyInput_emptyOutput() {
        Map<String, LocationRecord> testLocations = new HashMap<>();
        Calculations calculations = new Calculations(5,18,5,35);
        Map<String, LocationRecord> filteredLocations = calculations.filterByRequirements(testLocations);
        assertEquals(0, filteredLocations.size());
    }

    @Test
    void testCalculateValueFromFormula_validInput_validOutput() {
        Map<String, LocationRecord> testLocations = new HashMap<>();

        testLocations.put("Location1", new LocationRecord("name1",10L,15L,new GeographicalCoordinatesRecord("0","0")));
        Long score = testLocations.get("Location1").windSpeed()*3 + testLocations.get("Location1").temperature();

        Calculations calculations = new Calculations(5,18,5,35);
        Map<String, Long> actualOutput = calculations.calculateValueFromFormula(testLocations);

        assertEquals(score,actualOutput.get("Location1").longValue());
    }

    @Test
    void testCalculateValueFromFormula_nullInput_validOutputEmptyMap() {

        Calculations calculations = new Calculations(5,18,5,35);
        Map<String, Long> actualOutput = calculations.calculateValueFromFormula(null);

        assertEquals(Collections.emptyMap(),actualOutput);
    }

}
