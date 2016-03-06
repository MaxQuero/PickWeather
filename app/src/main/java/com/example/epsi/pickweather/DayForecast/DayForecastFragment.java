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
import android.widget.Toast;

import com.example.epsi.pickweather.Adapters.DayForecastAdapter;
import com.example.epsi.pickweather.Home.POJO.CurrentWeather;
import com.example.epsi.pickweather.Home.POJO.WeatherGenerator;
import com.example.epsi.pickweather.Home.RestInterface;
import com.example.epsi.pickweather.R;
import com.example.epsi.pickweather.SearchCity.SearchResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MaxQ on 06/03/2016.
 */
public class DayForecastFragment extends Fragment {
    private ImageButton myimagebtn;
    private RecyclerView recyclerView;
    private EditText myedittext;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.day_forecast_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        final RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);

        final LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);



        final RestInterface myrestinterface = WeatherGenerator.callAPI(RestInterface.class);
        Map<String, String> p = new HashMap<String, String>();
        p.put("q", "Lil");
        p.put("lang", "fr");
        p.put("mode", "json");
        p.put("type", "like");
        p.put("units", "metric");
        p.put("appid", "f48fbd8a004dce121b1720eb6fac9fc7");
        myrestinterface.getCity(p, new Callback<SearchResult>() {
            @Override
            public void success(SearchResult searchResult, Response response) {
                //System.out.println(response.toString());
                //System.out.println(sw.getMessage().toString());
                final ArrayList<CurrentWeather> myarray = new ArrayList<>();
                // Toast.makeText(getApplicationContext(), sw.getMessage().toString(), Toast.LENGTH_LONG).show();
                for (CurrentWeather currentw : searchResult.getMyresult()) {
                    myarray.add(currentw);
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