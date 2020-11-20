package com.fitnesstracker.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.util.Log;

public class Application extends android.app.Application {
	private static final String TAG = "Application";

	public static final String REMINDER_CHANNEL_ID = "0";

	@Override
	public void onCreate() {
		super.onCreate();

		Log.d(TAG, "Application onCreate()");


	}

	private void createNotificationChannel() {

	}
}
