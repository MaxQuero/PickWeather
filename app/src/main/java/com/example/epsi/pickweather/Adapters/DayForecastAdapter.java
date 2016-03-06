package com.example.epsi.pickweather.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.epsi.pickweather.Home.DayForecastPOJO.DayForecast;
import com.example.epsi.pickweather.R;

import java.util.ArrayList;

/**
 * Created by MaxQ on 06/03/2016.
 */
public class DayForecastAdapter extends RecyclerView.Adapter<DayForecastAdapter.TodayWeatherViewHolder>{

    Activity myActivity ;
    ArrayList<DayForecast> myArray;
    private static LayoutInflater myLayout = null;
    private Context mycontext;

    public DayForecastAdapter(Activity myactivity, int resource, ArrayList<DayForecast> myarray){
        this.myActivity = myactivity;
        this.myArray = myarray;
        this.mycontext = myactivity.getApplicationContext();
        myLayout = (LayoutInflater) myactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public DayForecast getItem(int position) {
        return this.myArray.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class TodayWeatherViewHolder extends RecyclerView.ViewHolder {
        TextView daytime, weather_icon, celcius_icon, temp;
        int icon, weatherCode;

        TodayWeatherViewHolder(View itemView) {
            super(itemView);
            temp = (TextView) itemView.findViewById(R.id.temperature);
            weather_icon = (TextView) itemView.findViewById(R.id.weather_ic);
            daytime = (TextView) itemView.findViewById(R.id.day_time);
            temp = (TextView) itemView.findViewById(R.id.temperature);
            TextView celcius_icon = (TextView) itemView.findViewById((R.id.celcius_ic));
        }
        public void putWeatherIcons(DayForecast df){

            weatherCode = df.getWeather().get(0).getId();


            switch (weatherCode){
                case 500 :
                    icon=R.string.light_rain;
                    weather_icon.setTextColor(Color.parseColor("#0091ea"));
                    break;
                case 800:
                    icon = R.string.sunny;
                    weather_icon.setTextColor(Color.parseColor("#ffeb3b"));

                    break;
                case 801:
                    icon = R.string.few_clouds;
                    weather_icon.setTextColor(Color.parseColor("#ffeb3b"));
                    break;
                case 905:
                    icon = R.string.windy;
                    weather_icon.setTextColor(Color.parseColor("#ffffff"));
                    break;
                default :
                    int simpleId = weatherCode/100;
                    switch (simpleId) {
                        case 2:
                            icon = R.string.thunder;
                            weather_icon.setTextColor(Color.parseColor("#fbea2b"));
                            break;
                        case 3:
                            icon = R.string.drizzle;
                            weather_icon.setTextColor(Color.parseColor("#ffffff"));
                            break;
                        case 5:
                            icon = R.string.rainy;
                            weather_icon.setTextColor(Color.parseColor("#82b2e4"));
                            break;
                        case 6:
                            icon = R.string.snowy;
                            weather_icon.setTextColor(Color.parseColor("#a0dfe4de"));
                            break;
                        case 7 : icon = R.string.foggy;
                            weather_icon.setTextColor(Color.parseColor("#ffffff"));
                            break;
                        case 8:
                            icon = R.string.cloudy;
                            weather_icon.setTextColor(Color.parseColor("#ffffff"));
                            break;
                    }
            }
            weather_icon.setText(icon);
            double c = df.getMain().getTemp().intValue() - 273;
            int celcius_degree = (int) c;

            temp.setText(String.valueOf(celcius_degree));

            if (celcius_degree < 10){
                temp.setTextColor(Color.parseColor("#0091ea"));
                celcius_icon.setTextColor(Color.parseColor("#0091ea"));
            } else if (10<=celcius_degree & celcius_degree<=20){
                temp.setTextColor(Color.parseColor("#4caf50"));
                celcius_icon.setTextColor(Color.parseColor("#4caf50"));
            } else if (20<celcius_degree & celcius_degree<=30){
                temp.setTextColor(Color.parseColor("#e65100"));
                celcius_icon.setTextColor(Color.parseColor("#e65100"));
            } else {
                temp.setTextColor(Color.parseColor("#b71c1c"));
                celcius_icon.setTextColor(Color.parseColor("#b71c1c"));
            }

        }

    }

    @Override
    public TodayWeatherViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_dayforecast, viewGroup, false);
        TodayWeatherViewHolder pvh = new TodayWeatherViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(TodayWeatherViewHolder twViewHolder, int i) {
        double c = this.myArray.get(i).getMain().getTemp().intValue() - 273 ;
        int celcius_degree = (int) c;
        twViewHolder.daytime.setText(String.valueOf(this.myArray.get(i).getDt()));
        twViewHolder.temp.setText(String.valueOf(celcius_degree) + "°C");

    }

    @Override
    public int getItemCount() {
        return myArray.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}