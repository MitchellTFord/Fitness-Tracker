package com.fitnesstracker.ui.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.fitnesstracker.R;
import com.fitnesstracker.database.FTViewModel;
import com.fitnesstracker.ui.Application;
import com.fitnesstracker.ui.notifications.NotificationPublisher;

import java.util.Random;

/**
 * An activity for configuring the app's settings and accessing information and features useful for
 * debugging.
 *
 * @author Mitchell Ford
 */
public class SettingsActivity extends AppCompatActivity {

	/**
	 * Tag for {@link Log} messages.
	 */
	public static final String TAG = "SettingsActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);

		// Open a new SettingsFragment if this activity had no saved instance state
		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.settings, SettingsFragment.newInstance())
					.commit();
		}

		// Set up the support action bar
//		ActionBar actionBar = getSupportActionBar();
//		if (actionBar != null) {
//			actionBar.setDisplayHomeAsUpEnabled(true);
//		}
	}

	/**
	 * A fragment that contains the app's settings and allows the user to access information and
	 * features useful for debugging.
	 */
	public static class SettingsFragment extends PreferenceFragmentCompat {

		/**
		 * Tag for {@link Log} messages.
		 */
		public static final String TAG = "SettingsActivity.SettingsFragment";

		/**
		 * The {@link FTViewModel} this fragment uses to access the database.
		 */
		private FTViewModel viewModel;

		/**
		 * Required empty public constructor.
		 */
		public SettingsFragment() {
		}

		/**
		 * A factory method that creates a new instance of this fragment.
		 *
		 * @return A new instance of SettingsFragment
		 */
		public static SettingsFragment newInstance() {
			return new SettingsFragment();
		}

		@Override
		public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

			// Retrieve a ViewModel to use for accessing the database
			viewModel = ViewModelProviders.of(requireActivity()).get(FTViewModel.class);

			// Retrieve the current preferences from storage
			setPreferencesFromResource(R.xml.root_preferences, rootKey);

			// Set up the clear database button to bring up a dialog confirming that the user
			// wishes to clear all data from the database
			Preference clearDataBase = findPreference("clear_db");
			assert clearDataBase != null;
			clearDataBase.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override public boolean onPreferenceClick(Preference preference) {
					// Open a dialog box for the user to confirm their choice
					new AlertDialog.Builder(requireContext())

							// Set title and message
							.setTitle(R.string.clear_db_title)
							.setMessage(R.string.clear_db_confirmation_dialog)

							// Clear all DB tables when the user presses "yes"
							.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
								@Override public void onClick(DialogInterface dialog, int which) {
									viewModel.clearAllTables();
									Toast.makeText(requireContext(), R.string.clear_db_message, Toast.LENGTH_SHORT).show();
								}
							})

							// Do nothing when the user presses "no"
							.setNegativeButton(R.string.no, null)
							.setIcon(R.drawable.ic_warning)
							.show();
					return true;
				}
			});

			// Set up the test notification button to send a notification to the user when clicked.
			final Preference sendTestNotification = findPreference("send_test_notification");
			assert sendTestNotification != null;
			sendTestNotification.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override public boolean onPreferenceClick(Preference preference) {
					sendTestNotification();
					return true;
				}
			});
		}

		/**
		 * Send a test notification on the reminder channel.
		 */
		private void sendTestNotification() {
			Log.d(TAG, "Sending test notification");

			// Set up the notification builder
			NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), Application.REMINDER_CHANNEL_ID)
					.setContentTitle("Test Notification Content Title")
					.setContentText("Test Notification Content Text")
					.setSmallIcon(R.drawable.ic_settings)
					.setPriority(NotificationCompat.PRIORITY_DEFAULT)
					.setAutoCancel(true);

			// Build the notification and push it to the user
			NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
			notificationManager.notify(NotificationPublisher.TEST_NOTIFICATION_ID, builder.build());
		}

	}
}