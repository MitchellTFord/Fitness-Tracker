package com.fitnesstracker.ui.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
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

import java.util.Random;

public class SettingsActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);
		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.settings, new SettingsFragment())
					.commit();
		}
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	public static class SettingsFragment extends PreferenceFragmentCompat {

		private FTViewModel viewModel;

		@Override
		public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

			viewModel = ViewModelProviders.of(requireActivity()).get(FTViewModel.class);

			setPreferencesFromResource(R.xml.root_preferences, rootKey);

			Preference clearDataBase = findPreference("clear_db");
			assert clearDataBase != null;
			clearDataBase.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override public boolean onPreferenceClick(Preference preference) {
					// Open a dialog box for the user to confirm their choice
					new AlertDialog.Builder(requireContext())
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
			NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), Application.REMINDER_CHANNEL_ID)
					.setContentTitle("Test Notification Content Title")
					.setContentText("Test Notification Content Text")
					.setSmallIcon(R.drawable.ic_settings)
					.setPriority(NotificationCompat.PRIORITY_DEFAULT)
					.setAutoCancel(true);

			NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
			notificationManager.notify(new Random().nextInt(), builder.build());
		}

	}
}