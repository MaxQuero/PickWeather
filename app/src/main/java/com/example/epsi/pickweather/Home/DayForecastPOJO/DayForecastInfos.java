
package com.example.epsi.pickweather.Home.DayForecastPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DayForecastInfos {

    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<DayForecast> listInfos = new ArrayList<DayForecast>();

    /**
     * 
     * @return
     *     The city
     */
    public City getCity() {
        return city;
    }

    /**
     * 
     * @param city
     *     The city
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * 
     * @return
     *     The cod
     */
    public String getCod() {
        return cod;
    }

    /**
     * 
     * @param cod
     *     The cod
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    /**
     * 
     * @return
     *     The message
     */
    public Double getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(Double message) {
        this.message = message;
    }

    /**
     * 
     * @return
     *     The cnt
     */
    public Integer getCnt() {
        return cnt;
    }

    /**
     * 
     * @param cnt
     *     The cnt
     */
    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    /**
     * 
     * @return
     *     The list
     */
    public java.util.List<DayForecast> getListInfos() {
        return listInfos;
    }

    /**
     * 
     * @param listInfos
     *     The list
     */
    public void setListInfos(java.util.List<DayForecast> listInfos) {
        this.listInfos = listInfos;
    }

}
