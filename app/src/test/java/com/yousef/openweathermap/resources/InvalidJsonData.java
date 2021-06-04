package com.yousef.openweathermap.resources;

public class InvalidJsonData {
    public String invalidCity = "{\"city\": {\n" +
            "\"rank\": 7,\n" +
            "\"location\": {\"lat\": 48.8588376, \"lon\": 2.2768491}\n" +
            "}\n" +
            "}";
    public String emptyCity = "{\"country\": {\n" +
            "\"rank\": 7,\n" +
            "\"location\": {\"lat\": 48.8588376, \"lon\": 2.2768491}\n" +
            "}\n" +
            "}";
}
