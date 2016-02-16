package com.example.epsi.pickweather.Home;

import com.example.epsi.pickweather.Home.POJO.CurrentWeather;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by MaxQuero on 29/01/2016.
 */
public interface RestInterface {


    @GET("/weather?q=Madagascar&APPID=f48fbd8a004dce121b1720eb6fac9fc7")
    void getWheatherReport(Callback<CurrentWeather> cb);


    @GET("/data/2.5/weather?q={city}&APPID={appid}f48fbd8a004dce121b1720eb6fac9fc7")
    Callback<CurrentWeather> searchWeather(@Query("q") String city);

    @GET("/weather")
    void getWeatherReport(@Query("q") String city, @Query("APPID") String appid,  Callback<CurrentWeather> callback);
}
