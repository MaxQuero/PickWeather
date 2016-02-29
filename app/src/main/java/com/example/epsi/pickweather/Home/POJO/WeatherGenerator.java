package com.example.epsi.pickweather.Home.POJO;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;

/**
 * Created by Camille on 27/02/2016.
 */
public class WeatherGenerator {
    public static final String API_URL_CITY = "http://api.openweathermap.org/data/2.5/";

    private static RestAdapter.Builder builderAutoCompletionCity = new RestAdapter.Builder()
            .setEndpoint(API_URL_CITY)
            .setLog(new AndroidLog("retrofit"))
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setClient(new OkClient(new OkHttpClient()));

    public static <S> S createAutoCompletCity(Class<S> serviceClass)
    {
        RestAdapter adapter = builderAutoCompletionCity.build();
        return adapter.create(serviceClass);
    }
}
