package com.example.epsi.pickweather.Home.SQlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.epsi.pickweather.Home.POJO.CurrentWeather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Camille on 02/03/2016.
 */
public abstract class DAO {
    // Champs de la base de données
    protected SQLiteDatabase database;
    protected MySQLiteHelper dbHelper;
    protected String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NAME };

    public DAO(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    protected void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    protected void close() {
        database.close();
    }
}
