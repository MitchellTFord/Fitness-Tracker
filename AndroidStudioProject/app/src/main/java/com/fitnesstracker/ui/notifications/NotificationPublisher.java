package com.fitnesstracker.ui.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationPublisher extends BroadcastReceiver {

	public static final int DAILY_REMINDER_NOTIFICATION_ID = 0;

	public static final String KEY_NOTIFICATION_ID = "notification-id";
	public static final String KEY_NOTIFICATION = "notification";

	@Override
	public void onReceive(Context context, Intent intent) {

		// Get the NotificationManager system service
		NotificationManager notificationManager = context.getSystemService(NotificationManager.class);

		// Get the notification from the intent
		Notification notification = intent.getParcelableExtra(KEY_NOTIFICATION);

		// Get the notification id from the intent
		int id = intent.getIntExtra(KEY_NOTIFICATION_ID, 0);

		// Send the notification to the user
		notificationManager.notify(id, notification);
	}
}
