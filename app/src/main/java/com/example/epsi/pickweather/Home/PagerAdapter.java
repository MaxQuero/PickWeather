package com.example.epsi.pickweather.Home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.epsi.pickweather.Home.POJO.CurrentWeather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Camille on 07/03/2016.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    private List<CurrentWeather> cw;

    public PagerAdapter(FragmentManager fm, ArrayList<CurrentWeather> cw) {
        super(fm);
        this.cw = cw;
    }

    @Override
    public Fragment getItem(int arg0) {
        return Fragment1.newInstance(cw.get(arg0));
    }

    @Override
    public int getCount() {
        return this.cw.size();

    }



}
