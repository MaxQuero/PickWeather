package com.example.epsi.pickweather.DayForecast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epsi.pickweather.Adapters.DayForecastAdapter;
import com.example.epsi.pickweather.Home.DayForecastPOJO.DayForecast;
import com.example.epsi.pickweather.Home.POJO.WeatherGenerator;
import com.example.epsi.pickweather.Home.RestInterface;
import com.example.epsi.pickweather.R;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MaxQ on 06/03/2016.
 */
public class DayForecastFragment extends Fragment {
    private ImageButton myimagebtn;
    private RecyclerView recyclerView;
     TextView daytime, weather_icon, celcius_icon, temp;
    int icon, weatherCode;
   private EditText myedittext;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.day_forecast_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        temp = (TextView) view.findViewById(R.id.temperature);
        weather_icon = (TextView) view.findViewById(R.id.weather_ic);
        daytime = (TextView) view.findViewById(R.id.day_time);
        temp = (TextView) view.findViewById(R.id.temperature);

        TextView celcius_icon = (TextView) view.findViewById((R.id.celcius_ic));
        {

            final RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);

            final LinearLayoutManager llm = new LinearLayoutManager(getContext());
            llm.setOrientation(LinearLayoutManager.HORIZONTAL);


            final RestInterface myrestinterface = WeatherGenerator.callAPI(RestInterface.class);

            myrestinterface.getDayForecastById(4517009, "f48fbd8a004dce121b1720eb6fac9fc7", new Callback<DayForecastResult>() {

                @Override
                public void success(DayForecastResult listDayForecast, Response response) {
                    //System.out.println(response.toString());
                    //System.out.println(sw.getMessage().toString());
                    final ArrayList<DayForecast> myarray = new ArrayList<>();
                    // Toast.makeText(getApplicationContext(), sw.getMessage().toString(), Toast.LENGTH_LONG).show();
                    for (DayForecast currentDfi : listDayForecast.getResult()) {
                        myarray.add(currentDfi);
                    }
                    final DayForecastAdapter adapter = new DayForecastAdapter(getActivity(), R.layout.element_dayforecast, myarray);
                    rv.setLayoutManager(llm);

                    rv.setAdapter(adapter);

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }

            });
        }
    }
}