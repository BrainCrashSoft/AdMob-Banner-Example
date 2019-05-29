package com.google.android.gms.example.bannerexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class BatteryReceiver extends BroadcastReceiver {


	@Override
	public void onReceive(Context context, Intent intent) {

		if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

			if (prefs != null) {
				int prevLevel = prefs.getInt("level", -1);

				int currentLevel = intent.getIntExtra("level", 0);

				// Only update display if something changed.
				if (prevLevel != currentLevel) {

					SharedPreferences.Editor editor = prefs.edit();
					editor.putInt("level", currentLevel);
					editor.apply();

					Intent i = new Intent("com.google.android.gms.example.bannerexample.UPDATE");
					i.setClass(context, BatteryWidget.class);
					context.sendBroadcast(i);

				}
			}

		}
	}

}