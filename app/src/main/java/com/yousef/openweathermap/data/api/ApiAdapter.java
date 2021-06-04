package com.yousef.openweathermap.data.api;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAdapter {
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private Context context;

    private ApiAdapter(Context context) {
        this.context = context;
    }

    public static ApiAdapter getInstance(Context context) {
        return new ApiAdapter(context);
    }

    private Retrofit provideRestAdapter() {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttp.getOkHttpClient(context, true))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public <S> S provideService(Class<S> serviceClass) {
        return provideRestAdapter().create(serviceClass);
    }
}