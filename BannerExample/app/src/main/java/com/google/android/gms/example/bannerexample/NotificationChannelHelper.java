package com.google.android.gms.example.bannerexample;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

class NotificationChannelHelper extends ContextWrapper {

    public static final String PRIMARY_CHANNEL = "BA01";
    public static final String SECONDARY_CHANNEL = "BA02";

    public NotificationChannelHelper(Context context, String channelId) {
        super(context);

        if (Build.VERSION.SDK_INT >= 26) {

            NotificationChannel channel = new NotificationChannel(channelId, "Battery", NotificationManager.IMPORTANCE_LOW);
            channel.setDescription("Battery notification channel.");
            channel.setShowBadge(false);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);

        }
    }

}
