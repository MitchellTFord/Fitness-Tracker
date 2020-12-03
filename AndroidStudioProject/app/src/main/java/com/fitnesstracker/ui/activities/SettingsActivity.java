package com.fitnesstracker.ui.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.fitnesstracker.R;
import com.fitnesstracker.database.FTViewModel;

public class SettingsActivity extends AppCompatActivity {
	private Switch myswitch;

	@SuppressLint("MissingSuperCall")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
			setTheme(R.style.darktheme);
		} else setTheme(R.style.AppTheme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);
		myswitch = findViewById(R.id.myswitch);
		if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
			myswitch.setChecked(true);
		}
		myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
					restartApp();
				} else {
					AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
					restartApp();
				}
			}
		});
	}

	public void restartApp() {
		Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
		startActivity(i);
		finish();
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
