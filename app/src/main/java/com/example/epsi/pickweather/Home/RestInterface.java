package com.example.epsi.pickweather.Home;

import com.example.epsi.pickweather.Home.POJO.CurrentWeather;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by MaxQuero on 29/01/2016.
 */
public interface RestInterface {

    @GET("/weather")
    void getWeatherReportByCityName(@Query("q") String city, @Query("APPID") String appid,  Callback<CurrentWeather> callback);

    @GET("/weather")
    void getWeatherReportByCoord(@Query("lat") String lat, @Query("lon") String lon, @Query("APPID") String appid,  Callback<CurrentWeather> callback);
    //http://api.openweathermap.org/data/2.5/weather?lat=50.3724754&lon=3.0872311&APPID=f48fbd8a004dce121b1720eb6fac9fc7

}
