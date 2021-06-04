package com.yousef.openweathermap.data.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TemperatureResponse {
    @Expose
    @SerializedName("main")
    public Main main;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public class Main {
        @SerializedName("temp")
        @Expose
        public float temp;

        public float getTemp() {
            return temp;
        }

        public void setTemp(float temp) {
            this.temp = temp;
        }
    }
}