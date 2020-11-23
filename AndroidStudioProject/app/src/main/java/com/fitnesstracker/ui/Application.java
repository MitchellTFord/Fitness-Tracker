package com.fitnesstracker.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

/**
 * The entry point of this application.
 * <p>
 * This class is identified in <code>AndroidManifest.xml</code>.
 * <p>
 * Currently, the only reason this class is needed is to initialize notification channels.
 */
public class Application extends android.app.Application {

	/**
	 * Tag for {@link Log} messages.
	 */
	private static final String TAG = "Application";

	/**
	 * The ID of the channel that daily reminder notifications should be sent on.
	 */
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

		// Notification channels are only required on API 26+
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

			// Initialize the attributes of the new channel
			CharSequence name = "Reminders";
			String description = "Reminders to create diary entries";
			int importance = NotificationManager.IMPORTANCE_DEFAULT;

			// Initialize the channel
			NotificationChannel channel = new NotificationChannel(REMINDER_CHANNEL_ID, name, importance);
			channel.setDescription(description);

			// Commit this channel to the notification manager system service
			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}
	}
}
