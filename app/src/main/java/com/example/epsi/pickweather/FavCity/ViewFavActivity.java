package com.example.epsi.pickweather.FavCity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.example.epsi.pickweather.Adapters.ViewPagerFavAdapter;
import com.example.epsi.pickweather.R;
import com.example.epsi.pickweather.SQlite.AccessBDDCity;
import com.viewpagerindicator.LinePageIndicator;

/**
 * Created by Camille on 07/03/2016.
 */
public class ViewFavActivity extends FragmentActivity {

    private ViewPagerFavAdapter mypageadapt;
    private ViewPager vppager;
    private LinePageIndicator mIndicator;

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

        mypageadapt = new ViewPagerFavAdapter(getSupportFragmentManager(), myaccess.getAllFav());
        vppager.setAdapter(mypageadapt);

        // ViewPager Indicator
        mIndicator = (LinePageIndicator) findViewById(R.id.indicator);
        //mIndicator.setFades(false);
        mIndicator.setViewPager(vppager);

        myaccess.close();
    }
}
