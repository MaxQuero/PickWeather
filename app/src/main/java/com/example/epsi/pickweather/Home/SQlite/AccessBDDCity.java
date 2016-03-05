package com.example.epsi.pickweather.Home.SQlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.example.epsi.pickweather.Home.POJO.CurrentWeather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Camille on 02/03/2016.
 */
public class AccessBDDCity extends DAO {
    public AccessBDDCity(Context context) {
        super(context);
    }

    public void open() throws SQLException {
        super.open();
    }

    public void close() {
        super.close();
    }

    public void createFav(CurrentWeather weather) {
        ContentValues myvalue = new ContentValues();
        myvalue.put(MySQLiteHelper.COLUMN_ID, weather.getId());
        myvalue.put(MySQLiteHelper.COLUMN_NAME, weather.getName());
        super.database.insert(MySQLiteHelper.TABLE_WEATHER, null, myvalue);
    }

    public void deleteFav(CurrentWeather current) {
        long id = current.getId();
        database.delete(MySQLiteHelper.TABLE_WEATHER, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public ArrayList<CurrentWeather> getAllFav() {
        String rawquery = "SELECT * FROM " + MySQLiteHelper.TABLE_WEATHER + ";";
        Cursor c = super.database.rawQuery(rawquery, null);
        return this.cursorToWeather(c);
    }

    private ArrayList<CurrentWeather> cursorToWeather(Cursor cursor) {
        if(cursor.getCount() == 0)
        {
            return null;
        }
        ArrayList<CurrentWeather> myweather = new ArrayList<>();
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_NAME));
            CurrentWeather current = new CurrentWeather();
            current.setId(id);
            current.setName(name);

            myweather.add(current);
        }
        cursor.close();
        return myweather;
    }
}
