package com.fitnesstracker.ui.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * A {@link BroadcastReceiver} for pushing notifications to the user.
 */
public class NotificationPublisher extends BroadcastReceiver {

	/**
	 * Tag for {@link Log} messages.
	 */
	private static final String TAG = "NotificationPublisher";

	/**
	 * The ID of the daily logging reminder notification.
	 */
	public static final int DAILY_REMINDER_NOTIFICATION_ID = 0;

	/**
	 * The key for the notification ID extra in <code>intent</code>.
	 */
	public static final String KEY_NOTIFICATION_ID = "notification-id";

	/**
	 * The key for the notification object extra in <code>intent</code>.
	 */
	public static final String KEY_NOTIFICATION = "notification";

	/**
	 * {@inheritDoc}
	 * <p>
	 * It's expected that <code>intent</code> with have a {@link Notification} extra with key {@link
	 * NotificationPublisher#KEY_NOTIFICATION} and an <code>int</code> extra representing the
	 * notification's <code>id</code> with key {@link NotificationPublisher#KEY_NOTIFICATION_ID}.
	 * <p>
	 * If these extras are not present, this method will return early and not send a notification.
	 */
	@Override
	public void onReceive(Context context, Intent intent) {

		// Get the NotificationManager system service
		NotificationManager notificationManager = context.getSystemService(NotificationManager.class);

		// Get the notification from the intent
		Notification notification = intent.getParcelableExtra(KEY_NOTIFICATION);

		// Check that a notification was successfully passed
		if (notification == null) {
			Log.d(TAG, "Notification not properly passed");
			return;
		}

		// Get the notification id from the intent
		int id = intent.getIntExtra(KEY_NOTIFICATION_ID, Integer.MIN_VALUE);

		// Check that the notification ID was successfully passed
		if (id == Integer.MIN_VALUE) {
			Log.d(TAG, "Notification ID not properly passed");
			return;
		}

		// Send the notification to the user
		notificationManager.notify(id, notification);
	}
}
