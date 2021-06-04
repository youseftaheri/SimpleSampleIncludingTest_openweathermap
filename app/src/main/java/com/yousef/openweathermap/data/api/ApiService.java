package com.yousef.openweathermap.data.api;


import com.yousef.openweathermap.data.model.TemperatureResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("weather?appid=bec2ea2f434c848c09196f2de96e3c4c")
    Observable<TemperatureResponse> getCityTemperature(
            @Query("q") String city_name);
}
