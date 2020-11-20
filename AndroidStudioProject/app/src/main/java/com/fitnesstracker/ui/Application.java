package com.fitnesstracker.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

public class Application extends android.app.Application {
	private static final String TAG = "Application";

	public static final String REMINDER_CHANNEL_ID = "0";

	@Override
	public void onCreate() {
		super.onCreate();

		Log.d(TAG, "onCreate()");

		createNotificationChannel();
	}

	/**
	 * Initialize the notification channels used by this app.
	 */
	private void createNotificationChannel() {

		// Create notification channels only on 26+
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			CharSequence name = "Reminders";
			String description = "Reminders to create diary entries";
			int importance = NotificationManager.IMPORTANCE_DEFAULT;

			NotificationChannel channel = new NotificationChannel(REMINDER_CHANNEL_ID, name, importance);
			channel.setDescription(description);

			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}
	}
}
