package com.example.epsi.pickweather.Home.POJO2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Camille on 28/02/2016.
 */
public class Sys {
    @SerializedName("country")
    @Expose
    private String country;

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
