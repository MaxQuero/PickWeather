package com.example.epsi.pickweather.Home;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.epsi.pickweather.Home.POJO.List;
import com.example.epsi.pickweather.R;

import java.util.ArrayList;

/**
 * Created by Camille on 27/02/2016.
 */
public class ListCityAdapter extends ArrayAdapter<List>{

    private Activity myActivity ;
    private ArrayList<List> myArray;
    private static LayoutInflater myLayout = null;

    public ListCityAdapter(Activity myactivity, int resource, ArrayList<List> myarray) {
        super(myactivity, resource, myarray);
        this.myActivity = myactivity;
        this.myArray = myarray;
        myLayout = (LayoutInflater) myactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.myArray.size();
    }

    @Override
    public List getItem(int position) {
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
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View myview = convertView;
        final ViewHolder holder;

        if(convertView == null ){
            myview = myLayout.inflate(R.layout.element_city, null);
            holder = new ViewHolder();
            holder.mytextviewname = (TextView) myview.findViewById(R.id.tv_cityname);
            holder.mytextviewcountry = (TextView) myview.findViewById(R.id.tv_country);
            holder.mytextviewtemp = (TextView) myview.findViewById(R.id.tv_temp);
            holder.myimageview = (ImageView) myview.findViewById(R.id.iv_Fav);

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

        return myview;
    }
}
