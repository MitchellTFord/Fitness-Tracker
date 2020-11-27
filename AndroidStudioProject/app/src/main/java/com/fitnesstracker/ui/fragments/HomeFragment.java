package com.fitnesstracker.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fitnesstracker.R;
import com.fitnesstracker.ui.activities.AddMealActivity;

/**
 * The fragment that first displays when {@link com.fitnesstracker.ui.activities.MainActivity} is
 * started.
 * <p>
 * This fragment contains shortcuts for adding meals and workouts, and for checking your progress on
 * meeting your goals.
 */
public class HomeFragment extends Fragment {

	/**
	 * Required empty public constructor.
	 */
	public HomeFragment() {
	}

	/**
	 * A factory method that creates a new instance of this fragment.
	 *
	 * @return A new instance of HomeFragment
	 */
	public static HomeFragment newInstance() {
		return new HomeFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_home, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Set up the add meal button
		Button addMealButton = view.findViewById(R.id.addmeal);
		addMealButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), AddMealActivity.class);
				startActivity(intent);
			}
		});

		// Set up the add workout button
		Button addWorkoutButton = view.findViewById(R.id.addworkout);
		addWorkoutButton.setOnClickListener(new View.OnClickListener() {
			// Display a toast saying "not yet implemented"
			@Override public void onClick(View v) {
				Toast.makeText(requireContext(),
						"Not yet implemented.",
						Toast.LENGTH_SHORT).show();
			}
		});

		// Set up the add workout button
		Button viewProgressButton = view.findViewById(R.id.viewprogress);
		viewProgressButton.setOnClickListener(new View.OnClickListener() {
			// Display a toast saying "not yet implemented"
			@Override public void onClick(View v) {
				Toast.makeText(requireContext(),
						"Not yet implemented.",
						Toast.LENGTH_SHORT).show();
			}
		});
	}
}