package com.example.epsi.pickweather.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.epsi.pickweather.Home.DayForecastPOJO.DayForecast;
import com.example.epsi.pickweather.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MaxQ on 06/03/2016.
 */
public class DayForecastAdapter extends RecyclerView.Adapter<DayForecastAdapter.DayWeatherViewHolder>{

    Activity myActivity ;

    private ArrayList<DayForecast> myArray;
    private static LayoutInflater myLayout = null;
    int icon, weatherCode;
    public DayForecastAdapter(Activity myactivity, int resource, ArrayList<DayForecast> myarray){
        this.myActivity = myactivity;
        this.myArray = myarray;
        myLayout = (LayoutInflater) myactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public DayForecast getItem(int position) {
        return this.myArray.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class DayWeatherViewHolder extends RecyclerView.ViewHolder {
        TextView daytime, weather_icon, celcius_icon, temp;
        LinearLayout lin;
        Typeface font;
        double c;
        int celcius_degree;
        DayWeatherViewHolder(View itemView) {
            super(itemView);
            temp = (TextView) itemView.findViewById(R.id.temperature);
            weather_icon = (TextView) itemView.findViewById(R.id.weather_ic);
            daytime = (TextView) itemView.findViewById(R.id.day_time);
            temp = (TextView) itemView.findViewById(R.id.temperature);
            celcius_icon = (TextView) itemView.findViewById((R.id.celcius_ic));
            lin = (LinearLayout) itemView.findViewById(R.id.linear);
            font = Typeface.createFromAsset(itemView.getContext().getAssets(), "climacons_webfont.ttf");


        }

    }

    @Override
    public DayWeatherViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_day_forecast, viewGroup, false);
        DayWeatherViewHolder pvh = new DayWeatherViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(DayWeatherViewHolder twViewHolder, int i) {
        DayForecast df = this.myArray.get(i);
        twViewHolder.c = df.getMain().getTemp().intValue() - 273 ;
        twViewHolder.celcius_degree = (int) twViewHolder.c;
        long timestamp = Long.parseLong(String.valueOf(df.getDt())) * 1000;
            String forecastHour = getDate(timestamp, true);
            twViewHolder.daytime.setText(forecastHour+'h');
            twViewHolder.temp.setText(String.valueOf(twViewHolder.celcius_degree) + "°C");
            twViewHolder.weather_icon.setTypeface(twViewHolder.font);
            twViewHolder.celcius_icon.setTypeface(twViewHolder.font);
            putWeatherIcons(this.myArray.get(i), twViewHolder);
    }

    @Override
    public int getItemCount() {
        return myArray.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    private String getDate(long timeStamp, Boolean hour){

        try{
            DateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat sdfHour = new SimpleDateFormat("HH");

            Date netDate = (new Date(timeStamp));

            if(hour) {
                return sdfHour.format(netDate);
            }else {
                return sdfDate.format(netDate);
            }
        }
        catch(Exception ex){
            return "xx";
        }
    }
    public void putWeatherIcons(DayForecast df, DayWeatherViewHolder tw){

        weatherCode = df.getWeather().get(0).getId();


        switch (weatherCode){
            case 500 :
                icon=R.string.light_rain;
                tw.weather_icon.setTextColor(Color.parseColor("#0091ea"));
                break;
            case 800:
                icon = R.string.sunny;
                tw.weather_icon.setTextColor(Color.parseColor("#ffeb3b"));

                break;
            case 801:
                icon = R.string.few_clouds;
                tw.weather_icon.setTextColor(Color.parseColor("#ffeb3b"));
                break;
            case 905:
                icon = R.string.windy;
                tw.weather_icon.setTextColor(Color.parseColor("#ffffff"));
                break;
            default :
                int simpleId = weatherCode/100;
                switch (simpleId) {
                    case 2:
                        icon = R.string.thunder;
                        tw.weather_icon.setTextColor(Color.parseColor("#fbea2b"));
                        break;
                    case 3:
                        icon = R.string.drizzle;
                        tw.weather_icon.setTextColor(Color.parseColor("#ffffff"));
                        break;
                    case 5:
                        icon = R.string.rainy;
                        tw.weather_icon.setTextColor(Color.parseColor("#82b2e4"));
                        break;
                    case 6:
                        icon = R.string.snowy;
                        tw.weather_icon.setTextColor(Color.parseColor("#a0dfe4de"));
                        break;
                    case 7 : icon = R.string.foggy;
                        tw.weather_icon.setTextColor(Color.parseColor("#ffffff"));
                        break;
                    case 8:
                        icon = R.string.cloudy;
                        tw.weather_icon.setTextColor(Color.parseColor("#ffffff"));
                        break;
                }
        }
        tw.weather_icon.setText(icon);

        tw.temp.setText(String.valueOf(tw.celcius_degree));
        tw.celcius_icon.setText(R.string.celcius);

        if (tw.celcius_degree < 10){
            tw.temp.setTextColor(Color.parseColor("#0091ea"));
            tw.celcius_icon.setTextColor(Color.parseColor("#0091ea"));
        } else if (10<=tw.celcius_degree & tw.celcius_degree<=20){
            tw.temp.setTextColor(Color.parseColor("#4caf50"));
            tw.celcius_icon.setTextColor(Color.parseColor("#4caf50"));
        } else if (20<tw.celcius_degree & tw.celcius_degree<=30){
            tw.temp.setTextColor(Color.parseColor("#e65100"));
            tw.celcius_icon.setTextColor(Color.parseColor("#e65100"));
        } else {
            tw.temp.setTextColor(Color.parseColor("#b71c1c"));
            tw.celcius_icon.setTextColor(Color.parseColor("#b71c1c"));
        }

    }

}