package com.example.epsi.pickweather.SearchCity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epsi.pickweather.Adapters.ListCityAdapter;
import com.example.epsi.pickweather.Home.MainActivity;
import com.example.epsi.pickweather.Home.POJO.CurrentWeather;
import com.example.epsi.pickweather.Home.POJO.WeatherGenerator;
import com.example.epsi.pickweather.Home.RestInterface;
import com.example.epsi.pickweather.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Camille on 27/02/2016.
 */
public class SearchCityActivity extends AppCompatActivity{
    private ImageButton myimagebtn;
    private ListView mylistview;
    private EditText myedittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.listcity);

        this.myimagebtn = (ImageButton) findViewById(R.id.btn_search);
        this.mylistview = (ListView) findViewById(R.id.listViewCity);
        this.myedittext = (EditText) findViewById(R.id.et_search);
        LinearLayout a =(LinearLayout) findViewById(R.id.linearCity);
        //Put padding to put layout under status bar
        a.setPadding(0, getStatusBarHeight()-10, 0, 0);

        //Search from keyboard "done" button click
        myedittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });
        //Search from image button click
        myimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });


    }
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void performSearch(){
        final RestInterface myrestinterface = WeatherGenerator.callAPI(RestInterface.class);


        Map<String, String> p = new HashMap<String, String>();
        p.put("q" , myedittext.getText().toString());
        p.put("lang" , "fr");
        p.put("mode" ,"json");
        p.put("type" , "like");
        p.put("units", "metric");
        p.put("appid", "f48fbd8a004dce121b1720eb6fac9fc7");

        if(myedittext.getText().length() > 2) {
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
                    final ListCityAdapter myadapt = new ListCityAdapter(SearchCityActivity.this, R.layout.element_city, myarray);
                    mylistview.setAdapter(myadapt);

                    mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                            Intent i = new Intent(SearchCityActivity.this, MainActivity.class);
                            i.putExtra("id", myarray.get(position).getId());
                            startActivity(i);

                        }
                    });

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }

            });

        }
    }
}