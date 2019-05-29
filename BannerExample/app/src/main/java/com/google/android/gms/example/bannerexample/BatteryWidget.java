package com.google.android.gms.example.bannerexample;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;

public class BatteryWidget extends AppWidgetProvider {

    //public final static String KEY_CURR_LEVEL = "level";
    //public static int mLevel = 0;


    public static int getNumberOfWidgets(final Context context) {
        ComponentName componentName = new ComponentName(context, BatteryWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] activeWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
        if (activeWidgetIds != null) {
            return activeWidgetIds.length;
        } else {
            return 0;
        }
    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);

        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(context, BatteryReceiver.class), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        if(getNumberOfWidgets(context) < 1) {
            try {
                context.stopService(new Intent(context, BatteryService.class));
            } catch (Exception e) {
                //Log.e(LOG_TAG,"",e);
            }
        }

    }

    @Override
    public void onEnabled(Context context) {
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(context, BatteryReceiver.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        ContextCompat.startForegroundService(context, new Intent(context, BatteryService.class));

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        AppWidgetManager gm = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = gm.getAppWidgetIds(new ComponentName(context, BatteryWidget.class));
        updateWidgets(context, gm, appWidgetIds);


    }

    private void updateWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            UpdateWidget(context, appWidgetManager, appWidgetId);
        }
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        updateWidgets(context, appWidgetManager, appWidgetIds);

    }


    public void UpdateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int mLevel = prefs.getInt("level", 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        views.setTextViewText(R.id.batteryText, ""+mLevel);

        Intent i = new Intent(context, MyActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);
        views.setOnClickPendingIntent(R.id.mainWidget, pi);

        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

}