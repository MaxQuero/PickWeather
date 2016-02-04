package com.example.maxq.pickweather.Home;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maxq.pickweather.Home.POJO.Model;
import com.example.maxq.pickweather.R;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;

public class MainActivity extends FragmentActivity {

    TextView city, status, humidity, pressure;
    String url = "http://api.openweathermap.org/data/2.5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city = (TextView) findViewById(R.id.txt_city);
        status = (TextView) findViewById(R.id.txt_status);
        humidity = (TextView) findViewById(R.id.txt_humidity);
        pressure = (TextView) findViewById(R.id.txt_press);

        //making object of RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(url)
                .setLog(new AndroidLog("retrofit"))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        //Creating Rest Services
        RestInterface restInterface = adapter.create(RestInterface.class);

        //Calling method to get whether report
        restInterface.getWeatherReport("Madagascar", "f48fbd8a004dce121b1720eb6fac9fc7", new Callback<Model>() {
            @Override
            public void success(Model model, Response response) {
                Toast.makeText(getApplicationContext(), String.format("OK"), Toast.LENGTH_SHORT).show();
                System.out.println(response.toString());
                city.setText("city :" + model.getName());
                status.setText("Status :" + model.getWeather().get(0).getDescription());
                humidity.setText("humidity :" + model.getMain().getHumidity().toString());
                pressure.setText("pressure :" + model.getMain().getPressure().toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), String.format("KO"), Toast.LENGTH_SHORT).show();

                String merror = error.getMessage();
            }
        });

    }
}