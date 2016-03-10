package com.example.epsi.pickweather.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epsi.pickweather.Home.POJO.CurrentWeather;
import com.example.epsi.pickweather.SQlite.AccessBDDCity;
import com.example.epsi.pickweather.R;

import java.util.ArrayList;

/**
 * Created by Camille on 04/03/2016.
 */
public class ListFavAdapter extends ArrayAdapter<CurrentWeather> {
    private Activity myActivity ;
    private ArrayList<CurrentWeather> myArray;
    private static LayoutInflater myLayout = null;
    private Context mycontext;

    public ListFavAdapter(Activity myactivity, int resource, ArrayList<CurrentWeather> myarray) {
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
        public TextView mytextviewname;
        public ImageButton myfavbtndelete;
    }

    private void createDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(mycontext);
        alert.setMessage("Etes vous sur de vouloir supprimer cette ville");
        alert.setCancelable(false);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mycontext, "cest ok", Toast.LENGTH_LONG).show();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.create().show();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View myview = convertView;
        final ViewHolder holder;

        if(convertView == null ){
            myview = myLayout.inflate(R.layout.element_fav, null);
            holder = new ViewHolder();
            holder.mytextviewname = (TextView) myview.findViewById(R.id.tv_cityname_fav);
            holder.myfavbtndelete = (ImageButton) myview.findViewById(R.id.ib_Fav_delete);

            myview.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) myview.getTag();
        }

        holder.mytextviewname.setText(this.myArray.get(position).getName());

        holder.myfavbtndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // createDialog();

                                AccessBDDCity myaccess = new AccessBDDCity(mycontext);
                                myaccess.open();
                                try {
                                    myaccess.deleteFav(myArray.get(position));
                                    myActivity.finish();
                                    myActivity.startActivity(myActivity.getIntent());
                                    Toast.makeText(mycontext, "Suppression réussi !", Toast.LENGTH_LONG).show();

                                } catch (Exception e) {
                                    Toast.makeText(mycontext, "Erreur lors de la suppression !", Toast.LENGTH_LONG).show();
                                } finally {
                                    myaccess.close();
                                }



            }
        });

        return myview;
    }
}
