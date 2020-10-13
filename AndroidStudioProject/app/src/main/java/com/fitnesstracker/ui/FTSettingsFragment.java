package com.fitnesstracker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fitnesstracker.R;
import com.fitnesstracker.database.FTDatabase;
import com.fitnesstracker.database.FTViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class FTSettingsFragment extends PreferenceFragmentCompat {

	private FTViewModel viewModel;

	@Override
	public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

		viewModel = ViewModelProviders.of(requireActivity()).get(FTViewModel.class);

		setPreferencesFromResource(R.xml.ft_preferences, rootKey);

		Preference clearDataBase = findPreference("clear_db");
		clearDataBase.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override public boolean onPreferenceClick(Preference preference) {
				viewModel.clearAllTables();
				Toast.makeText(requireContext(), "Cleared all tables", Toast.LENGTH_SHORT).show();
				return true;
			}
		});
	}
}