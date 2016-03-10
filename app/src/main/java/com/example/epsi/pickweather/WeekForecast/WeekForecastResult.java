package com.example.epsi.pickweather.WeekForecast;

import com.example.epsi.pickweather.Home.WeekForecastPOJO.WeekForecast;

/**
 * Created by MaxQ on 09/03/2016.
 */
public class WeekForecastResult {

    private WeekForecast[] list;


    public WeekForecastResult(WeekForecast[] myres) {
        this.list = myres;
    }

    public WeekForecast[] getResult() {
        return list;
    }

    public void setResult(WeekForecast[] myres) {
        this.list = myres;
    }
}
