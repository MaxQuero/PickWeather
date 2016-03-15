package com.example.epsi.pickweather.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.epsi.pickweather.FavCity.ViewPagerFavFragment;
import com.example.epsi.pickweather.Home.POJO.CurrentWeather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Camille on 07/03/2016.
 */
public class ViewPagerFavAdapter extends FragmentPagerAdapter {
    private List<CurrentWeather> cw;

    public ViewPagerFavAdapter(FragmentManager fm, ArrayList<CurrentWeather> cw) {
        super(fm);
        this.cw = cw;
    }

    @Override
    public Fragment getItem(int arg0) {
        return ViewPagerFavFragment.newInstance(cw.get(arg0));
    }

    @Override
    public int getCount() {
        return this.cw.size();

    }



}
