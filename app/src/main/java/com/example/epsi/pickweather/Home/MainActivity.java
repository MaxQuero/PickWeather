package com.example.epsi.pickweather.Home;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epsi.pickweather.Adapters.ForecastViewPagerAdapter;
import com.example.epsi.pickweather.FavCity.FavCityActivity;
import com.example.epsi.pickweather.R;
import com.example.epsi.pickweather.SQlite.AccessBDDCity;
import com.example.epsi.pickweather.SearchCity.SearchCityActivity;
import com.example.epsi.pickweather.Utils.ShakeDetector;
import com.example.epsi.pickweather.Utils.SlidingTabLayout;
import com.example.epsi.pickweather.Utils.URLCalls;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

/**
 * Created by MaxQuero on 29/01/2016.
 */

public class MainActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {
    GoogleApiClient mGoogleApiClient=null;
    Menu menu;


    String url = "http://api.openweathermap.org/data/2.5";
    Toolbar toolbar;
    ViewPager pager;
    ForecastViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Day","Week"};
    int Numboftabs =2;
    URLCalls urls;
    // Declaring the Toolbar Object
    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urls = new URLCalls(this);

        TextView celcius_icon, temp, weather_icon;
        weather_icon = (TextView) findViewById(R.id.weather_icon);

        //mLatitude = (TextView) findViewById(R.id.mLatitude);
        //mLongitude = (TextView) findViewById(R.id.mLongitude);


        //Set toolbar as action bar
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        // Set the padding to match the Status Bar height
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        // Start Google Location API

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
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
        urls.getWeatherById(urls.getWeatherId());
        Toast.makeText(getApplicationContext(), "Raffraichissement de " + urls.getWeatherName() + " .", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
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
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_favorite :

                AccessBDDCity myaccess = new AccessBDDCity(getApplicationContext());
                myaccess.open();

                if (myaccess.isAlreadyInsert(urls.getWeather())) {
                    Toast.makeText(getApplicationContext(), "Cette ville a été supprimé de vos favoris", Toast.LENGTH_LONG).show();

                    try {
                        myaccess.deleteFav(urls.getWeather());
                        item.setIcon(R.drawable.ic_favorite_white_18dp);
                        Toast.makeText(getApplicationContext(), "Suppression réussi !", Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Erreur lors de la suppression !", Toast.LENGTH_LONG).show();
                    } finally {
                        myaccess.close();
                    }
                } else {
                    try {
                        myaccess.createFav(urls.getWeather());
                        item.setIcon(R.drawable.favorite_icon);
                        Toast.makeText(getApplicationContext(), urls.getWeatherName() + " a bien été rajouté à vos favoris", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    } finally {
                        myaccess.close();
                    }
                }
                return true;
            case R.id.action_viewfav :
                Intent in = new Intent(MainActivity.this, ViewFavActivity.class);
                startActivity(in);
                return true;
            case R.id.action_fav :
                Intent i = new Intent(MainActivity.this, FavCityActivity.class);
                startActivity(i);
                return true;
            case R.id.action_geoloc :
                displayLocation();
                updateForecastData(null);
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
        //Get parameters from others activities
        urls.setLat(null);
        urls.setLon(null);
        Intent i = getIntent();
        final Integer idCity = (Integer) i.getSerializableExtra("id");
        if (idCity == null) {
            this.displayLocation();
        }else{
            urls.getWeatherById(idCity);
        }
        updateForecastData(idCity);
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
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    // A method to find height of the status bar
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void updateForecastData(final Integer id){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = new ForecastViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs, id, urls.getLat(), urls.getLon());

// Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.

                // Assigning ViewPager View and setting the adapter
                pager = (ViewPager) findViewById(R.id.pager);
                pager.setAdapter(adapter);


                // Assiging the Sliding Tab Layout View
                tabs = (SlidingTabLayout) findViewById(R.id.tabs);
                tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

                // Setting Custom Color for the Scroll bar indicator of the Tab View
                tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
                    @Override
                    public int getIndicatorColor(int position) {
                        return getResources().getColor(R.color.tabsScrollColor);
                    }
                });

                // Setting the ViewPager For the SlidingTabsLayout
                tabs.setViewPager(pager);
            }
        }, 200);
    }

    private void displayLocation() {

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            TextView mLatitudeText, mLongitudeText;
            /*mLatitudeText = (TextView) findViewById(R.id.mLatitude);
            mLongitudeText = (TextView) findViewById(R.id.mLongitude);
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));*/

            urls.setLat(String.valueOf(mLastLocation.getLatitude()));
            urls.setLon(String.valueOf(mLastLocation.getLongitude()));
            urls.getWeatherLocation(urls.getLat(), urls.getLon());
        }
    }




}
