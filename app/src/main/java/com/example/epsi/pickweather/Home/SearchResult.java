package com.example.epsi.pickweather.Home;

import com.example.epsi.pickweather.Home.POJO.CurrentWeather;

/**
 * Created by Camille on 27/02/2016.
 */
public class SearchResult {
    private CurrentWeather[] list;


    public SearchResult(CurrentWeather[] myres) {
        this.list = myres;
    }

    public CurrentWeather[] getMyresult() {
        return list;
    }

    public void setMyresult(CurrentWeather[] myres) {
        this.list = myres;
    }
}
