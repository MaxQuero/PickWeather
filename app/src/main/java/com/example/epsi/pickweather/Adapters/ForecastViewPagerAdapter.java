package com.example.epsi.pickweather.Adapters;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.epsi.pickweather.DayForecast.DayForecastFragment;
import com.example.epsi.pickweather.WeekForecast.WeekForecastFragment;

/**
 * Created by MaxQ on 06/03/2016.
 */
public class ForecastViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    String lat, lon;
    Integer cityId;
    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ForecastViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb, Integer id, String lat, String lon) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.cityId = id;
        this.lat = lat;
        this.lon = lon;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            final DayForecastFragment day_forecast = new DayForecastFragment();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    day_forecast.loadLatLon(cityId, lat, lon);

                }
            }, 5000);
            return day_forecast;


        }
        else // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            final WeekForecastFragment week_forecast = new WeekForecastFragment();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    week_forecast.loadLatLon(cityId, lat, lon);

                }
            }, 10000);
            return week_forecast;


        }
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}