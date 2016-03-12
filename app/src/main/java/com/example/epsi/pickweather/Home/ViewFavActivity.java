package com.example.epsi.pickweather.Home;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.example.epsi.pickweather.R;
import com.example.epsi.pickweather.SQlite.AccessBDDCity;

/**
 * Created by Camille on 07/03/2016.
 */
public class ViewFavActivity extends FragmentActivity {

    private PagerAdapter mypageadapt;
    private ViewPager vppager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.viewfav_city);

        initial();
    }

    private void initial() {

        AccessBDDCity myaccess = new AccessBDDCity(getApplicationContext());
        myaccess.open();
        myaccess.getAllFav();

        vppager = (ViewPager) findViewById(R.id.viewpager);

        mypageadapt = new PagerAdapter(getSupportFragmentManager(), myaccess.getAllFav());
        vppager.setAdapter(mypageadapt);

        myaccess.close();
    }
}