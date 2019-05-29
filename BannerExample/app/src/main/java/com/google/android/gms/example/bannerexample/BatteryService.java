package com.google.android.gms.example.bannerexample;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;


public class BatteryService extends Service {

    static BatteryReceiver mReceiver = new BatteryReceiver();

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String message = "Test battery";
            new NotificationChannelHelper(getApplicationContext(), NotificationChannelHelper.PRIMARY_CHANNEL);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationChannelHelper.PRIMARY_CHANNEL);
            builder.setContentTitle(getString(R.string.app_name));
            builder.setContentText(message);
            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
            builder.setSmallIcon(R.drawable.icon);
            //builder.setSound(null);
            builder.setPriority(Notification.FLAG_FOREGROUND_SERVICE);

            Notification notification = builder.build();

            startForeground(1, notification);
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_BATTERY_LOW);
        intentFilter.addAction(Intent.ACTION_BATTERY_OKAY);
        registerReceiver(mReceiver, intentFilter);

        return Service.START_STICKY;
    }


    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
