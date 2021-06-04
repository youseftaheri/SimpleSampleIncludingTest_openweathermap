package com.yousef.openweathermap;

import com.yousef.openweathermap.data.model.CityResponse;
import com.yousef.openweathermap.resources.InvalidJsonData;
import com.yousef.openweathermap.resources.ValidJsonData;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JsonParserTest {
    @Test
    public void testValidJSON(){
        ValidJsonData obj = new ValidJsonData();
        String jsonString = obj.paris;
        JsonParser jsonParser = new JsonParser();
        CityResponse.City city;
        city = jsonParser.getCity(jsonString);
        assertEquals(city.getName(), "Paris");
    }

    @Test
    public void testInvalidJSON(){
        InvalidJsonData obj = new InvalidJsonData();
        String jsonString = obj.invalidCity;
        JsonParser jsonParser = new JsonParser();
        CityResponse.City city;
        city = jsonParser.getCity(jsonString);
        assertNull(city.getName());
        assertEquals(7, city.getRank());
    }

    @Test
    public void testEmptyCityJSON(){
        InvalidJsonData obj = new InvalidJsonData();
        String jsonString = obj.emptyCity;
        JsonParser jsonParser = new JsonParser();
        CityResponse.City city;
        city = jsonParser.getCity(jsonString);
        assertNull(city);
    }

}
