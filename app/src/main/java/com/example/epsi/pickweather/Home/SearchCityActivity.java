package com.example.epsi.pickweather.Home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.epsi.pickweather.Home.POJO2.List;
import com.example.epsi.pickweather.Home.POJO.WeatherGenerator;
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

        final RestInterface myrestinterface = WeatherGenerator.createAutoCompletCity(RestInterface.class);

            myimagebtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Map<String, String> p = new HashMap<String, String>();
                  p.put("q" , myedittext.getText().toString());
                  p.put("lang" , "fr");
                  p.put("mode" ,"json");
                  p.put("type" , "like");
                  p.put("units", "metric");
                  p.put("appid" , "f48fbd8a004dce121b1720eb6fac9fc7");

                  if(myedittext.getText().length() > 2)
                  {
                      myrestinterface.getCity(p, new Callback<SearchResult>() {
                          @Override
                          public void success(SearchResult searchResult, Response response) {
                              //System.out.println(response.toString());
                              //System.out.println(sw.getMessage().toString());
                              ArrayList<List> myarray = new ArrayList<>();
                             // Toast.makeText(getApplicationContext(), sw.getMessage().toString(), Toast.LENGTH_LONG).show();
                              for (List currentw : searchResult.getMyresult()) {
                                  myarray.add(currentw);
                              }
                              final ListCityAdapter myadapt = new ListCityAdapter(SearchCityActivity.this, R.layout.element_city, myarray);
                             mylistview.setAdapter(myadapt);
                          }

                          @Override
                          public void failure(RetrofitError error) {
                              Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                          }

                      });


                      /*final retrofit.Callback<SearchResult> callback = new Callback<SearchResult>() {
                          @Override
                          public void success(SearchResult searchresult, Response response) {
                              System.out.println(response.toString());
                              ArrayList<SearchWeather> myarray = new ArrayList<>();

                              for (SearchWeather currentw : searchresult.getMyresult()){
                                  myarray.add(currentw);
                              }
                              final ListCityAdapter myadapt = new ListCityAdapter(SearchCityActivity.this, R.layout.element_city, myarray);
                              mylistview.setAdapter(myadapt);
                          }

                          @Override
                          public void failure(RetrofitError error) {
                              Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();
                          }
                      };
                      myrestinterface.getCity(p, callback);*/
                  }
              }
            }

        );
    }
}
