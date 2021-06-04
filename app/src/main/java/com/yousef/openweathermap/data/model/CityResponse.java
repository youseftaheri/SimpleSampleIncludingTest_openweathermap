package com.yousef.openweathermap.data.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityResponse {
    @Expose
    @SerializedName("city")
    public City city;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public static class City {
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("rank")
        @Expose
        public int rank;
        @SerializedName("location")
        @Expose
        public Location location;


        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public int getRank() {
            return rank;
        }
        public void setRank(int rank) {
            this.rank = rank;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }
    }

    public static class Location {

        @SerializedName("lat")
        @Expose
        public double lat;
        @SerializedName("lon")
        @Expose
        public double lon;

        public double getLat() {
            return lat;
        }
        public void setLat(double lat) { this.lat = lat; }

        public double getLon() {
            return lon;
        }
        public void setLon(double lon) { this.lon = lon; }
    }
}