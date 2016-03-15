package com.example.epsi.pickweather.WeekForecast;

import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epsi.pickweather.Adapters.WeekForecastAdapter;
import com.example.epsi.pickweather.Home.MainActivity;
import com.example.epsi.pickweather.Home.POJO.CallAPIWeather;
import com.example.epsi.pickweather.Home.RestInterface;
import com.example.epsi.pickweather.Home.WeekForecastPOJO.WeekForecast;
import com.example.epsi.pickweather.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MaxQ on 09/03/2016.
 */
public class WeekForecastFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    GoogleApiClient mGoogleApiClient = null;
    Typeface font;
    private RecyclerView rv_week;
    private LinearLayoutManager llm;
    String lat, lon;
    Integer id;

    public static WeekForecastFragment newInstance(Integer id, String lat, String lon) {
        WeekForecastFragment f1= new WeekForecastFragment();
        f1.id = id;
        f1.lat = lat;
        f1.lon = lon;
        return f1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.week_forecast_fragment, container, false);
        font = Typeface.createFromAsset(getActivity().getAssets(), "climacons_webfont.ttf");


        rv_week = (RecyclerView) v.findViewById(R.id.rv_week);
        llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final RestInterface myrestinterface = CallAPIWeather.callAPI(RestInterface.class);
                MainActivity a = new MainActivity();
                if (id == null) {
                    myrestinterface.getWeekForecastByLatLon(lat, lon, "fr", 5, "f48fbd8a004dce121b1720eb6fac9fc7", new Callback<WeekForecastResult>() {

                        @Override
                        public void success(WeekForecastResult listDayForecast, Response response) {
                            //System.out.println(response.toString());
                            //System.out.println(sw.getMessage().toString());
                            final ArrayList<WeekForecast> myarray = new ArrayList<>();
                            // Toast.makeText(getApplicationContext(), sw.getMessage().toString(), Toast.LENGTH_LONG).show();
                            for (WeekForecast currentDfi : listDayForecast.getResult()) {
                                long timestamp = Long.parseLong(String.valueOf(currentDfi.getDt())) * 1000;
                                myarray.add(currentDfi);
                            }
                            final WeekForecastAdapter adapter = new WeekForecastAdapter(getActivity(), R.layout.element_week_forecast, myarray);


                            rv_week.setLayoutManager(llm);

                            rv_week.setAdapter(adapter);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    });
                } else if (lon == null && lat == null) {

                    myrestinterface.getWeekForecastById(id, "fr", 5, "f48fbd8a004dce121b1720eb6fac9fc7", new Callback<WeekForecastResult>() {

                        @Override
                        public void success(WeekForecastResult listDayForecast, Response response) {
                            //System.out.println(response.toString());
                            //System.out.println(sw.getMessage().toString());
                            final ArrayList<WeekForecast> myarray = new ArrayList<>();
                            // Toast.makeText(getApplicationContext(), sw.getMessage().toString(), Toast.LENGTH_LONG).show();
                            for (WeekForecast currentDfi : listDayForecast.getResult()) {
                                long timestamp = Long.parseLong(String.valueOf(currentDfi.getDt())) * 1000;
                                myarray.add(currentDfi);
                            }
                            final WeekForecastAdapter adapter = new WeekForecastAdapter(getActivity(), R.layout.element_week_forecast, myarray);

                            rv_week.setLayoutManager(llm);

                            rv_week.setAdapter(adapter);

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    });
                    llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.HORIZONTAL);

                }
            }
        }, 600);
           return v;

        }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        { // Start Google Location API

        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            TextView mLatitudeText, mLongitudeText;
            /*mLatitudeText = (TextView) findViewById(R.id.mLatitude);
            mLongitudeText = (TextView) findViewById(R.id.mLongitude);
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));*/

            String mLat = String.valueOf(mLastLocation.getLatitude());
            String mLon = String.valueOf(mLastLocation.getLongitude());
            /*setLat(mLat);
            setLon(mLon);*/

        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}