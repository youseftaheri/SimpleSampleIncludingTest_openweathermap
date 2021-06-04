package com.yousef.openweathermap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.yousef.openweathermap.data.model.CityResponse;
import com.yousef.openweathermap.data.model.JsonData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JsonData obj = new JsonData();
        String jsonString = obj.paris;
        JsonParser jsonParser = new JsonParser();
        CityResponse.City city;
        city = jsonParser.getCity(jsonString);
        if (city != null) {
            Log.d("city name: ", city.getName());
            Log.d("city location lat: ", Double.toString(city.getLocation().getLat()));
        }
    }
}