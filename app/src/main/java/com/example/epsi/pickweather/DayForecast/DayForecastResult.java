package com.example.epsi.pickweather.DayForecast;


import com.example.epsi.pickweather.Home.DayForecastPOJO.DayForecast;

/**
 * Created by MaxQ on 06/03/2016.
 */
public class DayForecastResult {
    private DayForecast[] list;


    public DayForecastResult(DayForecast[] myres) {
        this.list = myres;
    }

    public DayForecast[] getResult() {
        return list;
    }

    public void setResult(DayForecast[] myres) {
        this.list = myres;
    }
}
