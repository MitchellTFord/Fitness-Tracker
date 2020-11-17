package com.fitnesstracker.ui.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.fitnesstracker.R;
import com.fitnesstracker.database.FTViewModel;

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
		}
	}
}