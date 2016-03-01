package com.example.epsi.pickweather.Home;

import com.example.epsi.pickweather.Home.POJO.List;

/**
 * Created by Camille on 27/02/2016.
 */
public class SearchResult {
    private List[] list;


    public SearchResult(List[] myres) {
        this.list = myres;
    }

    public List[] getMyresult() {
        return list;
    }

    public void setMyresult(List[] myres) {
        this.list = myres;
    }
}
