package com.example.epsi.pickweather.Utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epsi.pickweather.Home.POJO.CurrentWeather;
import com.example.epsi.pickweather.Home.RestInterface;
import com.example.epsi.pickweather.R;
import com.example.epsi.pickweather.SQlite.AccessBDDCity;
import com.example.epsi.pickweather.Home.POJO.WeatherGenerator;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MaxQ on 12/03/2016.
 */
public class URLCalls {
    public Activity a = new Activity();
    public TextView city, status, weather_icon, celcius_icon, humidity, pressure, temp, nameFromLocation, mLatitude, mLongitude;
    public int icon, weatherCode;
    public String currentCityName;
    public String mLat = null;
    public String mLon = null;

    public CurrentWeather currentWeather;

    public Integer cityId = null;
    public URLCalls(Activity b) {
        this.a = b;
        city = (TextView) b.findViewById(R.id.txt_city);
        weather_icon = (TextView) a.findViewById(R.id.weather_icon);
        status = (TextView) a.findViewById(R.id.txt_status);
        humidity = (TextView) a.findViewById(R.id.txt_humidity);
        pressure = (TextView) a.findViewById(R.id.txt_press);
        temp = (TextView) a.findViewById(R.id.temp);
        celcius_icon = (TextView) a.findViewById(R.id.celcius_icon);

        Typeface font = Typeface.createFromAsset(a.getAssets(), "climacons_webfont.ttf");
        weather_icon.setTypeface(font);
        celcius_icon.setTypeface(font);
    }



    public void getWeatherLocation(String lat, String lon) {
        final RestInterface r = WeatherGenerator.callAPI(RestInterface.class);
        r.getWeatherReportByCoord(lat, lon, "fr", "f48fbd8a004dce121b1720eb6fac9fc7", new Callback<CurrentWeather>() {
            @Override
            public void success(CurrentWeather weather, Response response) {
                System.out.println(response.toString());

                city.setText(weather.getName() + ", " + weather.getSys().getCountry());
                setWeatherName(weather.getName());
                setWeatherId(weather.getId());
                setWeather(weather);
                //Get simple weather code -> first number says wich type of weather it is
                status.setText("Temps actuel : " + weather.getWeather().get(0).getDescription());
                humidity.setText("Humidité : " + weather.getMain().getHumidity().intValue() + " %");
                pressure.setText("Pression : " + weather.getMain().getPressure().intValue() + " hpa");

                putWeatherIcons(weather);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(a.getApplicationContext(), String.format("KO"), Toast.LENGTH_SHORT).show();

                String merror = error.getMessage();
            }
        });
    }
    public void setWeatherName(String name){
        currentCityName = name;
    }
    public String getWeatherName(){ return currentCityName;}


    public void setWeatherId(int id){
        cityId = id;
    }
    public int getWeatherId() {
        return cityId;
    }

    public void setWeather(CurrentWeather w){
        currentWeather = w;
    }
    public CurrentWeather getWeather() {
        return currentWeather;
    }



    public void setLat(String l){
        mLat = l;
    }
    public void setLon(String lo){
        mLon = lo;
    }
    public String getLat(){
        return mLat;
    }
    public String getLon(){
        return mLon;
    }
    public void getWeatherById(int id) {
        final RestInterface ri = WeatherGenerator.callAPI(RestInterface.class);


        //Calling method to get weather report from city name
        ri.getWeatherReportById(id, "fr", "f48fbd8a004dce121b1720eb6fac9fc7", new Callback<CurrentWeather>() {
            @Override
            public void success(CurrentWeather weather, Response response) {
                System.out.println(response.toString());

                city.setText(weather.getName() + ", " + weather.getSys().getCountry());
                setWeatherName(weather.getName());
                setWeatherId(weather.getId());
                setWeather(weather);
                //Get simple weather code -> first number says wich type of weather it is
                status.setText("Temps actuel : " + weather.getWeather().get(0).getDescription());
                humidity.setText("Humidité : " + weather.getMain().getHumidity().intValue() + " %");
                pressure.setText("Pression : " + weather.getMain().getPressure().intValue() + " hpa");

                putWeatherIcons(weather);

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(a.getApplicationContext(), String.format("KO"), Toast.LENGTH_SHORT).show();

                String merror = error.getMessage();
            }
        });
    }


    public void putWeatherIcons(CurrentWeather weather) {
        weatherCode = weather.getWeather().get(0).getId();

        AccessBDDCity myaccess = new AccessBDDCity(a.getApplicationContext());
        myaccess.open();

        /*if (myaccess.isAlreadyInsert(weather)) {
            MenuItem item = menu.findItem(R.id.action_favorite);
            item.setIcon(R.drawable.favorite_icon);
        }*/

        double c = weather.getMain().getTemp().intValue() - 273;
        int celcius_degree = (int) c;
        LinearLayout view = (LinearLayout) a.findViewById(R.id.lin_layout);
        int bottom = view.getPaddingBottom();
        int top = view.getPaddingTop();
        int right = view.getPaddingRight();
        int left = view.getPaddingLeft();
        switch (weatherCode) {
            case 500:
                icon = R.string.light_rain;
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
            default:
                int simpleId = weatherCode / 100;
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
                    case 7:
                        icon = R.string.foggy;
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

        if (celcius_degree < 10) {
            temp.setTextColor(Color.parseColor("#0091ea"));
            celcius_icon.setTextColor(Color.parseColor("#0091ea"));
        } else if (10 <= celcius_degree & celcius_degree <= 20) {
            temp.setTextColor(Color.parseColor("#4caf50"));
            celcius_icon.setTextColor(Color.parseColor("#4caf50"));
        } else if (20 < celcius_degree & celcius_degree <= 30) {
            temp.setTextColor(Color.parseColor("#e65100"));
            celcius_icon.setTextColor(Color.parseColor("#e65100"));
        } else {
            temp.setTextColor(Color.parseColor("#b71c1c"));
            celcius_icon.setTextColor(Color.parseColor("#b71c1c"));
        }

    }
}
