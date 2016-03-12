/*
package com.example.epsi.pickweather.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.epsi.pickweather.Home.POJO.CurrentWeather;
import com.example.epsi.pickweather.R;

*/
/**
 * Created by MaxQ on 12/03/2016.
 *//*


public class WeatherWidget extends AppWidgetProvider {
    // Les tutos que propose notre widget

    CurrentWeather w;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        // Petite astuce : permet de garder la longueur du tableau sans accéder plusieurs fois à l'objet, d'où optimisation
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            // On récupère le RemoteViews qui correspond à l'AppWidget
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

            // On met le bon texte dans le bouton
            views.setTextViewText(R.id.txt_cit, "bite");

            // La prochaine section est destinée au bouton qui permet de passer au tuto suivant
            /*/
/********************************************************
            /*/
/*******************NEXT*********************************
            /*/
/********************************************************
        Intent intent = new Intent(context, WeatherWidget.class);

            // On veut que l'intent lance la mise à jour
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

            // On n'oublie pas les identifiants
           intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);


            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.actionButton, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, views);
        }
    }

}
*/
