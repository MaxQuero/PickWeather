package com.example.epsi.pickweather.Home;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Typeface;

import com.example.epsi.pickweather.Home.POJO.CurrentWeather;
import com.example.epsi.pickweather.Home.POJO.WeatherGenerator;
import com.example.epsi.pickweather.R;


import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by Camille on 07/03/2016.
 */
public class Fragment1 extends Fragment {
    private CurrentWeather currentw;

    public static Fragment1 newInstance(CurrentWeather cw) {
        Fragment1 f1= new Fragment1();
        f1.currentw = cw;
        return f1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.element_pageview, container, false);

        final TextView tvname = (TextView) v.findViewById(R.id.txt_name_frag);
        final TextView tvhumid = (TextView) v.findViewById(R.id.txt_humidity_frag);
        final TextView status = (TextView) v.findViewById(R.id.txt_status_frag);
        final TextView press = (TextView) v.findViewById(R.id.txt_press_frag);


        final RestInterface myrestinterface = WeatherGenerator.callAPI(RestInterface.class);
        myrestinterface.getWeatherReportById(currentw.getId(), "f48fbd8a004dce121b1720eb6fac9fc7", new Callback<CurrentWeather>() {
            @Override
            public void success(CurrentWeather weather, Response response) {
                tvname.setText(weather.getName());
                status.setText("Current Weather : " + weather.getWeather().get(0).getDescription());
                tvhumid.setText("humidity : " + weather.getMain().getHumidity().toString());
                press.setText("pressure : " + weather.getMain().getPressure().toString());

                putWeatherIcons(weather, v);
            }

            @Override
            public void failure(RetrofitError error) {
                String merror = error.getMessage();
            }
        });

        return v;
    }

    public void putWeatherIcons(CurrentWeather weather, View v){
        int weatherCode, icon;
        icon=R.string.light_rain;
        TextView weather_icon, celcius_icon, temp;
        weather_icon = (TextView) v.findViewById(R.id.weather_icon_frag);
        temp = (TextView) v.findViewById(R.id.temp_frag);
        celcius_icon = (TextView) v.findViewById(R.id.celcius_icon_frag);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "climacons_webfont.ttf");
        weather_icon.setTypeface(font);
        celcius_icon.setTypeface(font);

        weatherCode = weather.getWeather().get(0).getId();

        double c = weather.getMain().getTemp().intValue() - 273;
        int celcius_degree = (int) c;
        LinearLayout view = (LinearLayout) v.findViewById(R.id.lin_layout);
        int bottom = v.getPaddingBottom();
        int top = v.getPaddingTop();
        int right = v.getPaddingRight();
        int left = v.getPaddingLeft();
        switch (weatherCode){
            case 500 :
                icon=R.string.light_rain;
                weather_icon.setTextColor(Color.parseColor("#0091ea"));
                view.setBackgroundResource(R.drawable.light_rain);
                break;
            case 800:
                icon = R.string.sunny;
                weather_icon.setTextColor(Color.parseColor("#ffeb3b"));
                view.setBackgroundResource(R.drawable.sunny);

                break;
            case 801:
                icon = R.string.few_clouds;
                weather_icon.setTextColor(Color.parseColor("#ffeb3b"));
                view.setBackgroundResource(R.drawable.few_clouds);
                break;
            case 905:
                icon = R.string.windy;
                weather_icon.setTextColor(Color.parseColor("#ffffff"));
                view.setBackgroundResource(R.drawable.windy);
                break;
            default :
                int simpleId = weatherCode/100;
                switch (simpleId) {
                    case 2:
                        icon = R.string.thunder;
                        weather_icon.setTextColor(Color.parseColor("#fbea2b"));
                        view.setBackgroundResource(R.drawable.thunder);
                        break;
                    case 3:
                        icon = R.string.drizzle;
                        weather_icon.setTextColor(Color.parseColor("#ffffff"));
                        view.setBackgroundResource(R.drawable.drizzle);
                        break;
                    case 5:
                        icon = R.string.rainy;
                        weather_icon.setTextColor(Color.parseColor("#82b2e4"));
                        view.setBackgroundResource(R.drawable.rainy);
                        break;
                    case 6:
                        icon = R.string.snowy;
                        weather_icon.setTextColor(Color.parseColor("#a0dfe4de"));
                        view.setBackgroundResource(R.drawable.snowy);
                        break;
                    case 7 : icon = R.string.foggy;
                        weather_icon.setTextColor(Color.parseColor("#ffffff"));
                        view.setBackgroundColor(Color.parseColor("#9e9e9e"));
                        view.setBackgroundResource(R.drawable.foggy);
                        break;
                    case 8:
                        icon = R.string.cloudy;
                        weather_icon.setTextColor(Color.parseColor("#ffffff"));
                        view.setBackgroundResource(R.drawable.cloudy);
                        break;
                }
        }
        view.setPadding(left, top, right, bottom);
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
