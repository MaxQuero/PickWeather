package com.example.epsi.pickweather.Home.SQlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Camille on 02/03/2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_WEATHER = "comments";
    public static final String COLUMN_ID = "_id_city";
    public static final String COLUMN_NAME = "name";

    private static final String DATABASE_NAME = "weather.db";
    private static final int DATABASE_VERSION = 3;

    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE = "create table "
            + TABLE_WEATHER + "(" + COLUMN_ID
            + " integer primary key autoincrement, "+  COLUMN_NAME + " text not null);";

    public MySQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");*/
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER);
        onCreate(db);
    }
}
