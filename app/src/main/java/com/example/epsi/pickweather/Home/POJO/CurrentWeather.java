package com.example.epsi.pickweather.Home.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Camille on 28/02/2016.
 */
public class CurrentWeather {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("coord")
    @Expose
    private com.example.epsi.pickweather.Home.POJO.Coord coord;
    @SerializedName("main")
    @Expose
    private com.example.epsi.pickweather.Home.POJO.Main main;
    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("wind")
    @Expose
    private com.example.epsi.pickweather.Home.POJO.Wind wind;
    @SerializedName("sys")
    @Expose
    private com.example.epsi.pickweather.Home.POJO.Sys sys;
    @SerializedName("clouds")
    @Expose
    private com.example.epsi.pickweather.Home.POJO.Clouds clouds;
    @SerializedName("weather")
    @Expose
    private java.util.List<com.example.epsi.pickweather.Home.POJO.Weather> weather = new ArrayList<com.example.epsi.pickweather.Home.POJO.Weather>();
    @SerializedName("cod")
    @Expose
    private Integer cod;
    @SerializedName("base")
    @Expose
    private String base;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The coord
     */
    public com.example.epsi.pickweather.Home.POJO.Coord getCoord() {
        return coord;
    }

    /**
     *
     * @param coord
     * The coord
     */
    public void setCoord(com.example.epsi.pickweather.Home.POJO.Coord coord) {
        this.coord = coord;
    }

    /**
     *
     * @return
     * The main
     */
    public com.example.epsi.pickweather.Home.POJO.Main getMain() {
        return main;
    }

    /**
     *
     * @param main
     * The main
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     *
     * @return
     * The dt
     */
    public Integer getDt() {
        return dt;
    }

    /**
     *
     * @param dt
     * The dt
     */
    public void setDt(Integer dt) {
        this.dt = dt;
    }

    /**
     *
     * @return
     * The wind
     */
    public com.example.epsi.pickweather.Home.POJO.Wind getWind() {
        return wind;
    }

    /**
     *
     * @param wind
     * The wind
     */
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    /**
     *
     * @return
     * The sys
     */
    public com.example.epsi.pickweather.Home.POJO.Sys getSys() {
        return sys;
    }

    /**
     *
     * @param sys
     * The sys
     */
    public void setSys(Sys sys) {
        this.sys = sys;
    }

    /**
     *
     * @return
     * The clouds
     */
    public com.example.epsi.pickweather.Home.POJO.Clouds getClouds() {
        return clouds;
    }

    /**
     *
     * @param clouds
     * The clouds
     */
    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    /**
     *
     * @return
     * The weather
     */
    public java.util.List<com.example.epsi.pickweather.Home.POJO.Weather> getWeather() {
        return weather;
    }

    /**
     *
     * @param weather
     * The weather
     */
    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

}
