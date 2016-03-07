package com.example.epsi.pickweather.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.epsi.pickweather.Home.POJO.CurrentWeather;
import com.example.epsi.pickweather.Home.SQlite.AccessBDDCity;
import com.example.epsi.pickweather.R;

import java.util.ArrayList;

/**
 * Created by Camille on 04/03/2016.
 */
public class FavCityActivity extends AppCompatActivity{
    private ImageButton myimagebtn;
    private ListView mylistview;
    Toolbar toolbar;                              // Declaring the Toolbar Object
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.listcityfav);
        LinearLayout a =(LinearLayout) findViewById(R.id.linearCityFav);
        a.setPadding(0, getStatusBarHeight(), 0, 0);
        this.mylistview = (ListView) findViewById(R.id.listViewCityFav);

        final ArrayList<CurrentWeather> mylist = new ArrayList<CurrentWeather>();

        AccessBDDCity myaccess = new AccessBDDCity(getApplicationContext());

        myaccess.open();

        try {
            for (CurrentWeather cw : myaccess.getAllFav()) {
               mylist.add(cw);
            }
            //Toast.makeText(this, "fav selected", Toast.LENGTH_SHORT).show();
            final ListFavAdapter myadapt = new ListFavAdapter(FavCityActivity.this, R.layout.element_fav, mylist);
            mylistview.setAdapter(myadapt);

            mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    Intent i = new Intent(FavCityActivity.this, MainActivity.class);
                    i.putExtra("id", mylist.get(position).getId());
                    startActivity(i);

                }
            });

        } catch (Exception e) {

        } finally {
            myaccess.close();
        }
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

}
