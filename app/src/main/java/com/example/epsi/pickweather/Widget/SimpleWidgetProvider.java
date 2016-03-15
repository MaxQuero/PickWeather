package com.example.epsi.pickweather.Widget;

/**
 * Created by MaxQ on 12/03/2016.
 */

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.epsi.pickweather.Home.POJO.CurrentWeather;
import com.example.epsi.pickweather.Home.POJO.CallAPIWeather;
import com.example.epsi.pickweather.Home.RestInterface;
import com.example.epsi.pickweather.R;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SimpleWidgetProvider extends AppWidgetProvider {

    @Override

    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            final int widgetId = appWidgetIds[i];

            final RestInterface ri = CallAPIWeather.callAPI(RestInterface.class);


            //Calling method to get weather report from city name
            ri.getWeatherReportById(6429064, "fr", "f48fbd8a004dce121b1720eb6fac9fc7", new Callback<CurrentWeather>() {
                @Override
                public void success(CurrentWeather weather, Response response) {

                    double c = weather.getMain().getTemp().intValue() - 273;
                    int celcius_degree = (int) c;
                    final RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                            R.layout.widget);
                    System.out.println(response.toString());

                    remoteViews.setTextViewText(R.id.widget_city, weather.getName());
                    remoteViews.setTextViewText(R.id.widget_status, weather.getWeather().get(0).getDescription());
                    remoteViews.setTextViewText(R.id.widget_temp,String.valueOf(celcius_degree)+"°C");

                    Intent intent = new Intent(context, SimpleWidgetProvider.class);
                    intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                            0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    remoteViews.setOnClickPendingIntent(R.id.actionButton, pendingIntent);
                    appWidgetManager.updateAppWidget(widgetId, remoteViews);
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(context, String.format("KO"), Toast.LENGTH_SHORT).show();

                    String merror = error.getMessage();
                }
            });



         }
    }

}