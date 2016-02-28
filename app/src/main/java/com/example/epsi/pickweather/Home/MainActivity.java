package com.example.epsi.pickweather.Home;

import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epsi.pickweather.Home.POJO.CurrentWeather;
import com.example.epsi.pickweather.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;

/**
 * Created by MaxQuero on 29/01/2016.
 */

public class MainActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {
    GoogleApiClient mGoogleApiClient;

    TextView city, status, weather_icon, celcius_icon, humidity, pressure, temp, nameFromLocation;
    int icon, weatherCode;
    String url = "http://api.openweathermap.org/data/2.5";
    private Toolbar toolbar;                              // Declaring the Toolbar Object
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        city = (TextView) findViewById(R.id.txt_city);
        weather_icon = (TextView) findViewById(R.id.weather_icon);
        status = (TextView) findViewById(R.id.txt_status);
        humidity = (TextView) findViewById(R.id.txt_humidity);
        pressure = (TextView) findViewById(R.id.txt_press);
        nameFromLocation = (TextView) findViewById(R.id.cityNameFromCoord);
        temp = (TextView) findViewById(R.id.temp);
        weather_icon = (TextView) findViewById(R.id.weather_icon);
        celcius_icon = (TextView) findViewById(R.id.celcius_icon);

        Typeface font = Typeface.createFromAsset(getAssets(), "climacons_webfont.ttf");
        weather_icon.setTypeface(font);
        celcius_icon.setTypeface(font);
        //Set toolbar as action bar
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);


        // Start Google Location API
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        RestInterface ri = callAPI(url);

        //Calling method to get weather report from city name
        ri.getWeatherReportByCityName("Quebec", "f48fbd8a004dce121b1720eb6fac9fc7", new Callback<CurrentWeather>() {
            @Override
            public void success(CurrentWeather weather, Response response) {
                Toast.makeText(getApplicationContext(), String.format("OK"), Toast.LENGTH_SHORT).show();
                System.out.println(response.toString());

                city.setText(weather.getName() + ", " + weather.getSys().getCountry());
                //Get simple weather code -> first number says wich type of weather it is
                status.setText("Current Weather : " + weather.getWeather().get(0).getDescription());
                humidity.setText("humidity : " + weather.getMain().getHumidity().toString());
                pressure.setText("pressure : " + weather.getMain().getPressure().toString());

                putWeatherIcons(weather);

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), String.format("KO"), Toast.LENGTH_SHORT).show();

                String merror = error.getMessage();
            }
        });

    }

    public void putWeatherIcons(CurrentWeather weather){
        weatherCode = weather.getWeather().get(0).getId();
        double c = weather.getMain().getTemp().intValue() - 273;
        int celcius_degree = (int) c;
        View view = weather_icon.getRootView();

        switch (weatherCode){
            case 500 :
                icon=R.string.light_rain;
                weather_icon.setTextColor(Color.parseColor("#0091ea"));
                view.setBackgroundColor(Color.parseColor("#82b2e4"));
            case 800:
                icon = R.string.sunny;
                weather_icon.setTextColor(Color.parseColor("#fbea2b"));
                view.setBackgroundColor(Color.parseColor("#0091ea"));
                break;
            case 801:
                icon = R.string.few_clouds;
                weather_icon.setTextColor(Color.parseColor("#ffffff"));
                view.setBackgroundColor(Color.parseColor("#9e9e9e"));
                break;
            case 905:
                icon = R.string.windy;
                weather_icon.setTextColor(Color.parseColor("#ffffff"));
                view.setBackgroundColor(Color.parseColor("#9e9e9e"));
                break;
            default :
                int simpleId = weatherCode/100;
                switch (simpleId) {
                    case 2:
                        icon = R.string.thunder;
                        weather_icon.setTextColor(Color.parseColor("#fbea2b"));
                        view.setBackgroundColor(Color.parseColor("#9e9e9e"));

                        break;
                    case 3:
                        icon = R.string.drizzle;
                        weather_icon.setTextColor(Color.parseColor("#ffffff"));
                        view.setBackgroundColor(Color.parseColor("#9e9e9e"));

                        break;
                    case 5:
                        icon = R.string.rainy;
                        weather_icon.setTextColor(Color.parseColor("#82b2e4"));
                        view.setBackgroundColor(Color.parseColor("#9e9e9e"));

                        break;
                    case 6:
                        icon = R.string.snowy;
                        weather_icon.setTextColor(Color.parseColor("#a0dfe4de"));
                        view.setBackgroundColor(Color.parseColor("#9e9e9e"));

                        break;
                    case 7 : icon = R.string.foggy;
                        weather_icon.setTextColor(Color.parseColor("#ffffff"));
                        view.setBackgroundColor(Color.parseColor("#9e9e9e"));

                        break;
                    case 8:
                        icon = R.string.cloudy;
                        weather_icon.setTextColor(Color.parseColor("#ffffff"));
                        view.setBackgroundColor(Color.parseColor("#9e9e9e"));

                        break;
                }
        }
        weather_icon.setText(icon);
        temp.setText(String.valueOf(celcius_degree));
        celcius_icon.setText(R.string.celcius);

            if (celcius_degree < 10){
                temp.setTextColor(Color.parseColor("#0091ea"));
                celcius_icon.setTextColor(Color.parseColor("#0091ea"));
            } else if (11<celcius_degree & celcius_degree<20){
                temp.setTextColor(Color.parseColor("#4caf50"));
                celcius_icon.setTextColor(Color.parseColor("#4caf50"));
            } else if (21<celcius_degree & celcius_degree<30){
                temp.setTextColor(Color.parseColor("#e65100"));
                celcius_icon.setTextColor(Color.parseColor("#e65100"));
            } else {
                temp.setTextColor(Color.parseColor("#b71c1c"));
                celcius_icon.setTextColor(Color.parseColor("#b71c1c"));
            }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //Function to initialize OpenWeather API call. Create adapter which will be use to call
    //specific url.
    public RestInterface callAPI(String url) {
        //making object of RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(url)
                .setLog(new AndroidLog("retrofit"))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        //Creating Rest Services
        RestInterface restInterface = adapter.create(RestInterface.class);

        return restInterface;
    }


    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            String mLatitude, mLongitude;
            mLatitude = String.valueOf(mLastLocation.getLatitude());
            mLongitude = String.valueOf(mLastLocation.getLongitude());

            RestInterface ri = callAPI(url);

            ri.getWeatherReportByCoord(mLatitude, mLongitude, "f48fbd8a004dce121b1720eb6fac9fc7", new Callback<CurrentWeather>() {
                @Override
                public void success(CurrentWeather weather, Response response) {
                   /* Toast.makeText(getApplicationContext(), String.format("OK"), Toast.LENGTH_SHORT).show();
                    System.out.println(response.toString());

                    city.setText(weather.getName() + ", " + weather.getSys().getCountry());
                    //Get simple weather code -> first number says wich type of weather it is
                    status.setText("Current Weather : " + weather.getWeather().get(0).getDescription());
                    humidity.setText("humidity : " + weather.getMain().getHumidity().toString());
                    pressure.setText("pressure : " + weather.getMain().getPressure().toString());

                    putWeatherIcons(weather);*/

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplicationContext(), String.format("KO"), Toast.LENGTH_SHORT).show();

                    String merror = error.getMessage();
                }
            });
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}

