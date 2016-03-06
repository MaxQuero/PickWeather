package com.example.epsi.pickweather.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.epsi.pickweather.Home.POJO.CurrentWeather;
import com.example.epsi.pickweather.R;

import java.util.ArrayList;

/**
 * Created by MaxQ on 06/03/2016.
 */
public class DayForecastAdapter extends RecyclerView.Adapter<DayForecastAdapter.TodayWeatherViewHolder>{

    Activity myActivity ;ArrayList<CurrentWeather> myArray;
    private static LayoutInflater myLayout = null;
    private Context mycontext;

    public DayForecastAdapter(Activity myactivity, int resource, ArrayList<CurrentWeather> myarray){
        this.myActivity = myactivity;
        this.myArray = myarray;
        this.mycontext = myactivity.getApplicationContext();
        myLayout = (LayoutInflater) myactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public CurrentWeather getItem(int position) {
        return this.myArray.get(position);
    }


    public String getNameItem(int position) {
        return this.myArray.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public TodayWeatherViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_dayforecast, viewGroup, false);
        TodayWeatherViewHolder pvh = new TodayWeatherViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(TodayWeatherViewHolder twViewHolder, int i) {
        double c = this.myArray.get(i).getMain().getTemp().intValue();
        int celcius_degree = (int) c;
        twViewHolder.mytextviewname.setText(this.myArray.get(i).getName());
        twViewHolder.mytextviewcountry.setText(this.myArray.get(i).getSys().getCountry());
        twViewHolder.mytextviewtemp.setText(String.valueOf(celcius_degree) + "°C");
    }

    @Override
    public int getItemCount() {
        return myArray.size();
    }

    public static class TodayWeatherViewHolder extends RecyclerView.ViewHolder {
        TextView mytextviewname;
        TextView mytextviewcountry;
        TextView mytextviewtemp;



        TodayWeatherViewHolder(View itemView) {
            super(itemView);
            mytextviewname = (TextView) itemView.findViewById(R.id.tv_cityname);
            mytextviewcountry = (TextView) itemView.findViewById(R.id.tv_country);
            mytextviewtemp = (TextView) itemView.findViewById(R.id.tv_temp);
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}