package com.fitnesstracker.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fitnesstracker.R;
import com.fitnesstracker.database.FTDatabase;
import com.fitnesstracker.database.FTViewModel;
import com.fitnesstracker.database.Food;
import com.fitnesstracker.database.FoodDiaryEntry;
import com.fitnesstracker.database.Meal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiaryFragment extends Fragment {

	private FTViewModel viewModel;

	private FloatingActionButton addDiaryEntryFAB;

	public DiaryFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.

	 * @return A new instance of fragment DiaryFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static DiaryFragment newInstance() {
		DiaryFragment fragment = new DiaryFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_diary, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Get a view model
		viewModel = ViewModelProviders.of(requireActivity()).get(FTViewModel.class);

		// Set up the RecyclerView
		RecyclerView rv = (RecyclerView) view.findViewById(R.id.diary_recycler_view);
		rv.setLayoutManager(new LinearLayoutManager(getActivity()));

		// Set up the RecyclerView adapter
		final DiaryEntryAdapter adapter = new DiaryEntryAdapter();

		final TextView noDataTextView = requireView().findViewById(R.id.diary_rv_empty_text);

		// Observe database changes
		viewModel.getMeals().observe(getViewLifecycleOwner(), new Observer<List<Meal>>() {
			@Override public void onChanged(List<Meal> meals) {
				adapter.setData(meals);
				noDataTextView.setVisibility(meals == null || meals.isEmpty() ? View.VISIBLE : View.GONE);
			}
		});

		rv.setAdapter(adapter);

		// Set up the floating action button for adding new foods
		addDiaryEntryFAB = view.findViewById(R.id.add_diary_entry_fab);
		addDiaryEntryFAB.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				Toast.makeText(requireContext(),
						"Adding a random diary entry for testing",
						Toast.LENGTH_SHORT).show();
				generateSampleData();
			}
		});
	}

	public void generateSampleData() {
		Food food = Food.makeRandom();
		FoodDiaryEntry foodDiaryEntry = new FoodDiaryEntry(
				food,
				10*Math.random(),
				System.currentTimeMillis());
		viewModel.insert(food, foodDiaryEntry);
	}
}