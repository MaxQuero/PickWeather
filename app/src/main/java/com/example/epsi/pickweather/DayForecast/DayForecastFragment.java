package com.example.epsi.pickweather.DayForecast;

import android.graphics.Typeface;
import android.location.Location;
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
import com.example.epsi.pickweather.Home.MainActivity;
import com.example.epsi.pickweather.Home.POJO.WeatherGenerator;
import com.example.epsi.pickweather.Home.RestInterface;
import com.example.epsi.pickweather.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MaxQ on 06/03/2016.
 */
public class DayForecastFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private ImageButton myimagebtn;
    private RecyclerView recyclerView;
   private EditText myedittext;
    GoogleApiClient mGoogleApiClient=null;

    Typeface font;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.day_forecast_fragment, container, false);
         font = Typeface.createFromAsset(getActivity().getAssets(), "climacons_webfont.ttf");

        return v;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        { // Start Google Location API
            final RecyclerView rv_day = (RecyclerView) view.findViewById(R.id.rv_day);

            final LinearLayoutManager llm = new LinearLayoutManager(getContext());
            llm.setOrientation(LinearLayoutManager.HORIZONTAL);
            final RestInterface myrestinterface = WeatherGenerator.callAPI(RestInterface.class);
            MainActivity a = new MainActivity();
            myrestinterface.getDayForecastById(4517009, 5, "f48fbd8a004dce121b1720eb6fac9fc7", new Callback<DayForecastResult>() {

                @Override
                public void success(DayForecastResult listDayForecast, Response response) {
                    //System.out.println(response.toString());
                    //System.out.println(sw.getMessage().toString());
                    final ArrayList<DayForecast> myarray = new ArrayList<>();
                    // Toast.makeText(getApplicationContext(), sw.getMessage().toString(), Toast.LENGTH_LONG).show();
                    for (DayForecast currentDfi : listDayForecast.getResult()) {
                        long timestamp = Long.parseLong(String.valueOf(currentDfi.getDt())) * 1000;

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String forecastDate = getDate(timestamp, false);

                        String currentDate = sdf.format(new Date());
                        myarray.add(currentDfi);

                    }
                    final DayForecastAdapter adapter = new DayForecastAdapter(getActivity(), R.layout.element_day_forecast, myarray);

                    rv_day.setLayoutManager(llm);

                    rv_day.setAdapter(adapter);

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }

            });
        }
    }
    private String getDate(long timeStamp, Boolean hour){

        try{
            DateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat sdfHour = new SimpleDateFormat("HH");

            Date netDate = (new Date(timeStamp));

            if(hour) {
                return sdfHour.format(netDate);
            }else {
                return sdfDate.format(netDate);
            }
        }
        catch(Exception ex){
            return "xx";
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