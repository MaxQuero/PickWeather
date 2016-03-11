package com.example.epsi.pickweather.Home;

import com.example.epsi.pickweather.DayForecast.DayForecastResult;
import com.example.epsi.pickweather.Home.POJO.CurrentWeather;
import com.example.epsi.pickweather.SearchCity.SearchResult;
import com.example.epsi.pickweather.WeekForecast.WeekForecastResult;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by MaxQuero on 29/01/2016.
 */
public interface RestInterface {

    @GET("/weather")
    void getWeatherReportByCityName(@Query("q") String city, @Query("APPID") String appid,  Callback<CurrentWeather> callback);

    @GET("/weather")
    void getWeatherReportByCoord(@Query("lat") String lat, @Query("lon") String lon, @Query("lang") String lang,  @Query("APPID") String appid,  Callback<CurrentWeather> callback);
    //http://api.openweathermap.org/data/2.5/weather?lat=50.3724754&lon=3.0872311&APPID=f48fbd8a004dce121b1720eb6fac9fc7

    @GET("/weather")
    void getWeatherReportById(@Query("id") int id , @Query("lang") String lang, @Query("APPID") String appid,  Callback<CurrentWeather> callback);

    @GET("/find")
    void getCity(@QueryMap Map<String, String> c, Callback<SearchResult> callback);

    @GET("/forecast")
    void getDayForecastById(@Query("id") int id ,@Query("lang") String lang, @Query("cnt") int cnt, @Query("APPID") String appid, Callback<DayForecastResult> callback);

    @GET("/forecast")
    void getDayForecastByLatLon(@Query("lat") String lat, @Query("lon") String lon ,@Query("lang") String lang, @Query("cnt") int cnt, @Query("APPID") String appid, Callback<DayForecastResult> callback);

    @GET("/forecast/daily")
    void getWeekForecastById(@Query("id") int id ,@Query("lang") String lang, @Query("cnt") int cnt, @Query("APPID") String appid, Callback<WeekForecastResult> callback);

    @GET("/forecast/daily")
    void getWeekForecastByLatLon(@Query("lat") String lat, @Query("lon") String lon ,@Query("lang") String lang, @Query("cnt") int cnt, @Query("APPID") String appid, Callback<WeekForecastResult> callback);


}
