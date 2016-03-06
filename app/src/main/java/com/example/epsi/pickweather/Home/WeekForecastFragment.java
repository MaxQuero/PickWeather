package com.example.epsi.pickweather.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.epsi.pickweather.R;

/**
 * Created by MaxQ on 06/03/2016.
 */
public class WeekForecastFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.week_forecast_fragment,container,false);
        return v;
    }
}