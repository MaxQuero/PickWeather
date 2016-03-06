package com.example.epsi.pickweather.FavCity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.epsi.pickweather.Adapters.ListFavAdapter;
import com.example.epsi.pickweather.Home.POJO.CurrentWeather;
import com.example.epsi.pickweather.SQlite.AccessBDDCity;
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

        ArrayList<CurrentWeather> mylist = new ArrayList<>();

        AccessBDDCity myaccess = new AccessBDDCity(getApplicationContext());

        myaccess.open();

        try {
           /* for (CurrentWeather cw : myaccess.getAllFav()) {
                Toast.makeText(getApplicationContext(), cw.getName(), Toast.LENGTH_LONG).show();
            }*/
            //Toast.makeText(this, "fav selected", Toast.LENGTH_SHORT).show();
            mylist = myaccess.getAllFav();
            final ListFavAdapter myadapt = new ListFavAdapter(FavCityActivity.this, R.layout.element_fav, mylist);
            mylistview.setAdapter(myadapt);

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
