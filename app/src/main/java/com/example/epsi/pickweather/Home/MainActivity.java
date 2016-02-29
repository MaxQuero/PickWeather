package com.example.epsi.pickweather.Home;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epsi.pickweather.Home.POJO.CurrentWeather;
import com.example.epsi.pickweather.R;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;

public class MainActivity extends ActionBarActivity {

    TextView city, status, humidity, pressure;
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
        RestInterface ri = callAPI(url);


        //Calling method to get whether report
        ri.getWeatherReport("Madagascar", "f48fbd8a004dce121b1720eb6fac9fc7", new Callback<CurrentWeather>() {
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

    public void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ArrayList<LocationProvider> providers = new ArrayList<LocationProvider>();

        //ArrayList<String> names = locationManager.getProviders(true);


        /*for (String name : names){
            providers.add(locationManager.getProvider(name));
        }*/

    }

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
}
