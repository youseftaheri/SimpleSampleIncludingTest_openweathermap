package com.yousef.openweathermap.data.api;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttp {
    private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    public OkHttp() {
    }

    @NonNull
    static OkHttpClient getOkHttpClient(Context context, boolean debug) {
        // Install an HTTP cache in the context cache directory.
        File cacheDir = new File(context.getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);

        OkHttpClient.Builder builder = new OkHttpClient.Builder().cache(cache);
        builder.connectTimeout(5, TimeUnit.MINUTES).readTimeout(5, TimeUnit.MINUTES);

        if (debug) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        return builder.build();
    }

}