/*package com.example.epsi.pickweather.Home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maxq.pickweather.R;

import java.util.List;

import retrofit.Callback;

public class HomeActivity  extends Activity {
    private WebView wv = null;
    // private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=Paris&APPID=f48fbd8a004dce121b1720eb6fac9fc7";
    public static final String BASE_URL = "http://api.openweathermap.org";
    protected Context mContext;
    String Weathers ;
    TextView city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(R.layout.home_activity_main);
        //final ListView listView = (ListView) findViewById(R.id.list);
        Weathers = new String();

        city = new TextView(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenWeatherService service = retrofit.create(OpenWeatherService.class);

        //final Call<ListWeathers> repos = service.searchWeather("Paris");
        Call<Weather> wthr = service.srchWeather("Paris", "");
        wthr.enqueue(new Callback<Weather>() {

            @Override public void onResponse(Response<Weather> response, Retrofit retrofit) {
                Log.d("MainActivity", "Status Code = " + response.code());
                Toast.makeText(getApplicationContext(), String.format("OK"), Toast.LENGTH_SHORT).show();
                Weather result = response.body();
//                Log.d("MainActivity", "response = " + new Gson().toJson(result));
                Weathers = result.getCity();
                Log.v("MainActivity", "Items = " + response);
                //text.setText(Weathers);

               /* WeatherAdapter adapter = new WeatherAdapter(HomeActivity.this, Weathers);
                listView.setAdapter(adapter);*/
/*
            }
            @Override public void onFailure(Throwable t) {
                Toast.makeText(mContext, String.format("KO"), Toast.LENGTH_SHORT).show(); }
        });
    }

    public void afficherRepos(List<Weather> repos) {
        Toast.makeText(this,"nombre de dépots : "+repos.size(),Toast.LENGTH_SHORT).show();
    }

    /*protected void weatherHttpClient(){



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenWeatherService service = retrofit.create(OpenWeatherService.class);
        Call<ListWeathers> weather= service.searchWeather("Paris&APPID=f48fbd8a004dce121b1720eb6fac9fc7");
        weather.enqueue(new Callback<ListWeathers>() {


            @Override
            public void onResponse(Response<ListWeathers> response, Retrofit retrofit) {
                Toast.makeText(getApplicationContext(), String.format("OK"), Toast.LENGTH_SHORT).show();
                //List<Weather> allRepos = response.body();
                ListView listview = (ListView) findViewById(R.id.listView);
                //set adapter pour afficher listview?

                ArrayAdapter<Weather> adapter = (ArrayAdapter<Weather>) getListAdapter();
                adapter.clear();
                adapter.addAll(response.body().items);

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(mContext, String.format("KO"), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void afficherRepos(List<Weather> weath) {
        Toast.makeText(this,"nombre de dépots : "+weath.size(),Toast.LENGTH_SHORT).show();
    }
    protected void str () {



        HttpURLConnection con = null;
        InputStream is = null;
        TextView text = null;
        try {

            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=Paris&APPID=f48fbd8a004dce121b1720eb6fac9fc7");
            text = new TextView(this);


            HttpURLConnection urlConnection = (HttpURLConnection) (url.openConnection());
            InputStream stream = urlConnection.getInputStream();

            // Par exemple…

            stream.read();
            try {
                InputStream vf = null;
                URLConnection conn = url.openConnection();
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line;

                while ((line = rd.readLine()) != null) {
                    System.out.println(line);
                }
                rd.close();

                text.setText("baba");

                setContentView(text);
                //return stringBuilder.toString();
            } finally {
                urlConnection.disconnect();
            }
        }
        catch(Throwable t){

            text.setText("boubou");

            setContentView(text);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setProgressBarIndeterminateVisibility(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // prepare call in Retrofit 2.0
        OpenWeatherService stackOverflowAPI = retrofit.create(OpenWeatherService.class);

        Call<ListWeathers> call = stackOverflowAPI.searchWeather("Paris");
        //asynchronous call
        call.enqueue(this);

        // synchronous call would be with execute, in this case you
        // would have to perform this outside the main thread
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //to cancel a running request
        call.cancel();
        // calls can only be used once but you can easily clone them
        Call<ListWeathers> c = call.clone();
        c.enqueue(this);

        return true;
    }

    @Override
    public void onResponse(Response<ListWeathers> response, Retrofit retrofit) {
        setProgressBarIndeterminateVisibility(false);
        ArrayAdapter<Weather> adapter = (ArrayAdapter<Weather>) getListAdapter();
        adapter.clear();
        adapter.addAll(response.body().items);
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(HomeActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
}
*/