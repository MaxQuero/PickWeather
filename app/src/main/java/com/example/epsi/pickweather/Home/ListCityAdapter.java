package com.example.epsi.pickweather.Home;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epsi.pickweather.Home.POJO.CurrentWeather;
import com.example.epsi.pickweather.Home.SQlite.AccessBDDCity;
import com.example.epsi.pickweather.R;

import java.util.ArrayList;

/**
 * Created by Camille on 27/02/2016.
 */
public class ListCityAdapter extends ArrayAdapter<CurrentWeather>{

    private Activity myActivity ;
    private ArrayList<CurrentWeather> myArray;
    private static LayoutInflater myLayout = null;
    private Context mycontext;

    public ListCityAdapter(Activity myactivity, int resource, ArrayList<CurrentWeather> myarray) {
        super(myactivity, resource, myarray);
        this.myActivity = myactivity;
        this.myArray = myarray;
        this.mycontext = myactivity.getApplicationContext();
        myLayout = (LayoutInflater) myactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.myArray.size();
    }

    @Override
    public CurrentWeather getItem(int position) {
        return this.myArray.get(position);
    }


    public String getNameItem(int position) {
        return this.myArray.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView mytextviewname, mytextviewcountry, mytextviewtemp, celcius_icon;
        public ImageView myimageview;
        public ImageButton  myfavbtn;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View myview = convertView;
        final ViewHolder holder;

        if(convertView == null ){
            myview = myLayout.inflate(R.layout.element_city, null);
            holder = new ViewHolder();
            holder.mytextviewname = (TextView) myview.findViewById(R.id.tv_cityname);
            holder.mytextviewcountry = (TextView) myview.findViewById(R.id.tv_country);
            holder.mytextviewtemp = (TextView) myview.findViewById(R.id.tv_temp);
            holder.myimageview = (ImageView) myview.findViewById(R.id.iv_Fav);
            holder.myfavbtn = (ImageButton) myview.findViewById(R.id.IB_Fav);

            myview.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) myview.getTag();
        }
        double c = this.myArray.get(position).getMain().getTemp().intValue();
        int celcius_degree = (int) c;
        holder.mytextviewname.setText(this.myArray.get(position).getName());
        holder.mytextviewcountry.setText(this.myArray.get(position).getSys().getCountry());
        holder.mytextviewtemp.setText(String.valueOf(celcius_degree) + "°C");


        holder.myfavbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccessBDDCity myaccess = new AccessBDDCity(mycontext);
                myaccess.open();
                try {
                    myaccess.createFav(myArray.get(position));
                    Toast.makeText(mycontext, myArray.get(position).getName() + "a bien été rajouté à vos favoris", Toast.LENGTH_LONG).show();

                } catch (Exception e) {

                } finally {
                    myaccess.close();
                }

            }
        });

        return myview;
    }
}
