package com.yousef.openweathermap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.yousef.openweathermap.data.model.CityResponse;
import com.yousef.openweathermap.data.model.JsonData;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvCity)
    TextView tvCity;
    @BindView(R.id.tvRank)
    TextView tvRank;
    @BindView(R.id.tvLatitude)
    TextView tvLatitude;
    @BindView(R.id.tvLongitude)
    TextView tvLongitude;
    @BindView(R.id.tvTemperature)
    TextView tvTemperature;
    @BindView(R.id.btSubmit)
    TextView btSubmit;
    @BindView(R.id.etCity)
    TextView etCity;
    @BindView(R.id.etRank)
    TextView etRank;
    private ProgressDialog mProgressDialog;
    private Disposable disposable = null;
    private GpsTracker gpsTracker;
    CityResponse.City myCity = new CityResponse.City();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setMessage(getString(R.string.loading));

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        JsonData obj = new JsonData();
        String jsonString = obj.paris;
        JsonParser jsonParser = new JsonParser();
        CityResponse.City city;
        city = jsonParser.getCity(jsonString);
        if (city != null) {
            Log.d("city name: ", city.getName());
            Log.d("city location lat: ", Double.toString(city.getLocation().getLat()));
        }

        btSubmit.setOnClickListener(v -> {
            updateInfo();
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateInfo() {
        tvCity.setText(etCity.getText().toString());
        tvRank.setText(etRank.getText().toString());
        myCity.setName(etCity.getText().toString());
        myCity.setRank(Integer.parseInt(etRank.getText().toString()));
        setLocation();
    }

    public void setLocation(){
        gpsTracker = new GpsTracker(MainActivity.this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            tvLatitude.setText(String.valueOf(latitude));
            tvLongitude.setText(String.valueOf(longitude));
            CityResponse.Location location = new CityResponse.Location();
            location.setLat(latitude);
            location.setLon(longitude);
            myCity.setLocation(location);
        }else{
            gpsTracker.showSettingsAlert();
        }
    }
}