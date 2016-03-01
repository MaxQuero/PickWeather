package com.example.epsi.pickweather.Home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epsi.pickweather.Home.POJO.CurrentWeather;
import com.example.epsi.pickweather.Home.POJO.WeatherGenerator;
import com.example.epsi.pickweather.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MaxQuero on 29/01/2016.
 */

public class MainActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {
    GoogleApiClient mGoogleApiClient=null;

    TextView city, status, weather_icon, celcius_icon, humidity, pressure, temp, nameFromLocation;
    String currentCityName, mLat, mLon;
    int icon, weatherCode, cityId;
    String url = "http://api.openweathermap.org/data/2.5";
    private Toolbar toolbar;                              // Declaring the Toolbar Object
    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        Integer test = (Integer) i.getSerializableExtra("obj");

        if (test == null)
        {
            Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), String.valueOf(test), Toast.LENGTH_LONG).show();
        }

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

        Intent i = getIntent();

        Integer test = (Integer) i.getSerializableExtra("id");


            // and get whatever type user account id is

            if (test== null) {

            Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_LONG).show();
        } else {
                final RestInterface rs = WeatherGenerator.callAPI(RestInterface.class);
            rs.getWeatherReportById(test, "f48fbd8a004dce121b1720eb6fac9fc7", new Callback<CurrentWeather>() {
                @Override
                public void success(CurrentWeather weather, Response response) {
                    Toast.makeText(getApplicationContext(), String.format("OK"), Toast.LENGTH_SHORT).show();
                    System.out.println(response.toString());
                    setWeatherName(weather.getName());
                    setWeatherId(weather.getId());
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

        Toast.makeText(getApplicationContext(), String.valueOf(test), Toast.LENGTH_LONG).show();

    }





        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                handleShakeEvent();

            }
        });
    }

    public void handleShakeEvent(){
        Toast.makeText(getApplicationContext(), "Refreshing " + currentCityName + " weather", Toast.LENGTH_SHORT).show();
        final RestInterface ri = WeatherGenerator.callAPI(RestInterface.class);

        //Calling method to get weather report from city name
        ri.getWeatherReportById(cityId, "f48fbd8a004dce121b1720eb6fac9fc7", new Callback<CurrentWeather>() {
            @Override
            public void success(CurrentWeather weather, Response response) {
                Toast.makeText(getApplicationContext(), String.format("OK"), Toast.LENGTH_SHORT).show();
                System.out.println(response.toString());
                setWeatherName(weather.getName());
                setWeatherId(weather.getId());
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
             LinearLayout view = (LinearLayout) findViewById(R.id.lin_layout);
        int bottom = view.getPaddingBottom();
        int top = view.getPaddingTop();
        int right = view.getPaddingRight();
        int left = view.getPaddingLeft();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId() ){
            case R.id.action_search :
                Intent myIntent = new Intent(MainActivity.this, SearchCityActivity.class);
                startActivity(myIntent);
                return true;

            case R.id.action_settings :
                return true;

            default :
                return super.onOptionsItemSelected(item);
        }
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
        Intent i = getIntent();
        Integer test = (Integer) i.getSerializableExtra("id");
        if (test == null) {
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                TextView mLatitudeText, mLongitudeText;
                mLatitudeText = (TextView) findViewById(R.id.mLatitude);
                mLongitudeText = (TextView) findViewById(R.id.mLongitude);
                mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
                mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));

                mLat = String.valueOf(mLastLocation.getLatitude());
                mLon = String.valueOf(mLastLocation.getLongitude());

                final RestInterface r = WeatherGenerator.callAPI(RestInterface.class);
                r.getWeatherReportByCoord(mLat, mLon, "f48fbd8a004dce121b1720eb6fac9fc7", new Callback<CurrentWeather>() {
                    @Override
                    public void success(CurrentWeather weather, Response response) {
                        Toast.makeText(getApplicationContext(), String.format("OK"), Toast.LENGTH_SHORT).show();
                        System.out.println(response.toString());

                        city.setText(weather.getName() + ", " + weather.getSys().getCountry());
                        setWeatherName(weather.getName());
                        setWeatherId(weather.getId());
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
        }
    }

    public void setWeatherName(String name){
        currentCityName = name;
    }

    public void setWeatherId(int id){
        cityId = id;
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
}
