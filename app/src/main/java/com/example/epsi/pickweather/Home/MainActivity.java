package com.example.epsi.pickweather.Home;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epsi.pickweather.Home.POJO.CurrentWeather;
import com.example.epsi.pickweather.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.common.api.GoogleApiClient.*;

import com.google.android.gms.location.LocationServices;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;

/**
 * Created by MaxQuero and Camille on 29/01/2016.
 */

public class MainActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {
    GoogleApiClient mGoogleApiClient;

    TextView city, status, humidity, pressure, nameFromLocation;
    String url = "http://api.openweathermap.org/data/2.5";
    private Toolbar toolbar;                              // Declaring the Toolbar Object
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Set toolbar as action bar
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);


        city = (TextView) findViewById(R.id.txt_city);
        status = (TextView) findViewById(R.id.txt_status);
        humidity = (TextView) findViewById(R.id.txt_humidity);
        pressure = (TextView) findViewById(R.id.txt_press);
        nameFromLocation = (TextView) findViewById(R.id.cityNameFromCoord);
        RestInterface ri = callAPI(url);

        // Start Google Location API
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        //Calling method to get weather report from city name
        ri.getWeatherReportByCityName("Douai", "f48fbd8a004dce121b1720eb6fac9fc7", new Callback<CurrentWeather>() {
            @Override
            public void success(CurrentWeather weather, Response response) {
                Toast.makeText(getApplicationContext(), String.format("OK"), Toast.LENGTH_SHORT).show();
                System.out.println(response.toString());
                city.setText("city :" + weather.getName());
                status.setText("Status :" + weather.getWeather().get(0).getDescription());
                humidity.setText("humidity :" + weather.getMain().getHumidity().toString());
                pressure.setText("pressure :" + weather.getMain().getPressure().toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), String.format("KO"), Toast.LENGTH_SHORT).show();

                String merror = error.getMessage();
            }
        });

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
    //Remettre la condition (if) commme avant !!
    @Override
    public void onConnected(Bundle connectionHint) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
            TextView mLatitudeText, mLongitudeText;
            mLatitudeText = (TextView) findViewById(R.id.mLatitude);
            mLongitudeText = (TextView) findViewById(R.id.mLongitude);
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        String mLatitude;
        String mLongitude;
        mLatitude = String.valueOf(mLastLocation.getLatitude());
        mLongitude = String.valueOf(mLastLocation.getLongitude());

        RestInterface ri = callAPI(url);

        ri.getWeatherReportByCoord(mLatitude, mLongitude, "f48fbd8a004dce121b1720eb6fac9fc7", new Callback<CurrentWeather>() {
            @Override
            public void success(CurrentWeather weather, Response response) {
                Toast.makeText(getApplicationContext(), String.format("OK"), Toast.LENGTH_SHORT).show();
                System.out.println(response.toString());
                nameFromLocation.setText("Name Location :" + weather.getName());

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), String.format("KO"), Toast.LENGTH_SHORT).show();

                String merror = error.getMessage();
            }
        });
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}

