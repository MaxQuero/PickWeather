package com.example.epsi.pickweather.Utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.example.epsi.pickweather.Home.DayForecastPOJO.DayForecast;
import com.example.epsi.pickweather.R;

/**
 * Created by MaxQ on 09/03/2016.
 */
public class Utils {
    TextView weather_icon, celcius_icon, temp;
    int icon, weatherCode;
    Typeface font;

    public void putWeatherIcons(DayForecast df, Activity a, View itemView){
        font = Typeface.createFromAsset(a.getAssets(), "climacons_webfont.ttf");

        double c = df.getMain().getTemp().intValue() - 273 ;

        int celcius_degree = (int) c;
        weather_icon = (TextView) itemView.findViewById(R.id.weather_ic);
        temp = (TextView) itemView.findViewById(R.id.temperature);
        celcius_icon = (TextView) itemView.findViewById((R.id.celcius_ic));
        weatherCode = df.getWeather().get(0).getId();
        temp.setText(String.valueOf(celcius_degree) + "°C");
        weather_icon.setTypeface(font);
        celcius_icon.setTypeface(font);
        switch (weatherCode){
            case 500 :
                icon= R.string.light_rain;
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

        temp.setText(String.valueOf(celcius_degree));
        celcius_icon.setText(R.string.celcius);

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
