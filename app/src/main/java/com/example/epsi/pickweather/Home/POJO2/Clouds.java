package com.example.epsi.pickweather.Home.POJO2;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Camille on 28/02/2016.
 */
public class Clouds {
    @SerializedName("all")
    @Expose
    private Integer all;

    /**
     *
     * @return
     * The all
     */
    public Integer getAll() {
        return all;
    }

    /**
     *
     * @param all
     * The all
     */
    public void setAll(Integer all) {
        this.all = all;
    }
}
