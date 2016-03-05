package com.example.epsi.pickweather.Home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.epsi.pickweather.Home.POJO.CurrentWeather;
import com.example.epsi.pickweather.Home.SQlite.AccessBDDCity;
import com.example.epsi.pickweather.R;
import com.google.android.gms.ads.doubleclick.CustomRenderedAd;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Camille on 04/03/2016.
 */
public class FavCityActivity extends AppCompatActivity{
    private ImageButton myimagebtn;
    private ListView mylistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.listcityfav);

        this.mylistview = (ListView) findViewById(R.id.listViewCityFav);

        ArrayList<CurrentWeather> mylist = new ArrayList<>();

        AccessBDDCity myaccess = new AccessBDDCity(getApplicationContext());

        myaccess.open();

        try {
            /*for (CurrentWeather cw : myaccess.getAllFav()) {
                Toast.makeText(getApplicationContext(), cw.getName(), Toast.LENGTH_LONG).show();
            }*/
            Toast.makeText(this, "fav selected", Toast.LENGTH_SHORT).show();
            mylist = myaccess.getAllFav();
            final ListFavAdapter myadapt = new ListFavAdapter(FavCityActivity.this, R.layout.element_fav, mylist);
            mylistview.setAdapter(myadapt);

        } catch (Exception e) {

        } finally {
            myaccess.close();
        }
    }


}
