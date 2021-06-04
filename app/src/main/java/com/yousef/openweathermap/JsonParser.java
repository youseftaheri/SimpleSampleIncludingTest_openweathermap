package com.yousef.openweathermap;

import com.yousef.openweathermap.data.model.CityResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JsonParser {
    public CityResponse.City getCity(String jsonString) {
        try{
            Gson gson = new Gson();
            CityResponse cityResponse = gson.fromJson(jsonString, CityResponse.class);
            return cityResponse.getCity();
        } catch(JsonSyntaxException jse){
            System.out.println("Not a valid Json String:" + jse.getMessage());
            return null;
        }
    }
}
