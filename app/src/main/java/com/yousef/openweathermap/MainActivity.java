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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.yousef.openweathermap.data.api.ApiAdapter;
import com.yousef.openweathermap.data.api.ApiService;
import com.yousef.openweathermap.data.api.rx.RxApiCallHelper;
import com.yousef.openweathermap.data.api.rx.RxApiCallback;
import com.yousef.openweathermap.data.model.CityResponse;
import com.yousef.openweathermap.data.model.JsonData;
import com.yousef.openweathermap.data.model.TemperatureResponse;

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
    Button btSubmit;
    @BindView(R.id.etCity)
    TextView etCity;
    @BindView(R.id.etRank)
    TextView etRank;
    @BindView(R.id.tilCity)
    TextInputLayout tilCity;
    @BindView(R.id.tilRank)
    TextInputLayout tilRank;
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
            if(etCity.getText().toString().isEmpty())
                tilCity.setError(getString(R.string.empty_city));
            else if(etRank.getText().toString().isEmpty())
                tilRank.setError(getString(R.string.empty_rank));
            else {
                tilRank.setError(null);
                tilCity.setError(null);
                updateInfo();
                if (isInternetAvailable()) {
                    getTemperature();
                } else {
                    showToast(getString(R.string.no_internet_connection));
                }
            }
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
            tvLatitude.setText(String.format("%.5f", latitude));
            tvLongitude.setText(String.format("%.5f", longitude));
            CityResponse.Location location = new CityResponse.Location();
            location.setLat(latitude);
            location.setLon(longitude);
            myCity.setLocation(location);
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    private void getTemperature() {
        mProgressDialog.show();
        ApiService apiService = ApiAdapter.getInstance(this).provideService(ApiService.class);
        Observable<TemperatureResponse> responseObservable =
                apiService.getCityTemperature(etCity.getText().toString());
        disposable = RxApiCallHelper.call(responseObservable, new RxApiCallback<TemperatureResponse>() {
            @Override
            public void onSuccess(TemperatureResponse temperatureResponse) {
                mProgressDialog.dismiss();
                if(temperatureResponse != null) {
                    showToast(temperatureResponse.getMain() != null ? "Success" : "Failed");
                    if (temperatureResponse.getMain() != null) {
                        showTemperature(temperatureResponse);
                    }
                }else {
                    showToast("Something went wrong");
                }
                disposeCall();
            }

            @Override
            public void onFailed(Throwable throwable) {
                disposeCall();
                mProgressDialog.dismiss();
                showToast(throwable.getLocalizedMessage());
            }
        });
    }

    private void disposeCall() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @SuppressLint("SetTextI18n")
    private void showTemperature(TemperatureResponse temperatureResponse) {
        tvTemperature.setText(String.format("%.2f", (temperatureResponse.main.temp - 273.15F)) + " °C");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting()
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected();
    }
}