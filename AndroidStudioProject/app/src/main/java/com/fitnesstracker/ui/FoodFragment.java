package com.fitnesstracker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitnesstracker.R;
import com.fitnesstracker.database.FTViewModel;
import com.fitnesstracker.database.Food;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass. Use the {@link FoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodFragment extends Fragment {

	private FloatingActionButton addFoodFAB;

	private FTViewModel viewModel;

	public FoodFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of this fragment using the provided
	 * parameters.
	 *
	 * @return A new instance of fragment FoodFragment.
	 */
	@NotNull
	public static FoodFragment newInstance() {
		FoodFragment fragment = new FoodFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_food, container, false);
	}

	@Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		viewModel = ViewModelProviders.of(requireActivity()).get(FTViewModel.class);

		// Sample Data
		// ArrayList<Food> foods = new ArrayList<>();
		// foods.add(new Food("Apple", "apple", 1d));
		// foods.add(new Food("Orange", "orange", 2.5));

		// Set up the RecyclerView
		RecyclerView foodRV = (RecyclerView) view.findViewById(R.id.food_recycler_view);
		final FoodAdapter adapter = new FoodAdapter(new ArrayList<Food>());
		adapter.setEmptyRVHandler(new EmptyRVHandler() {
			@Override public void handleEmptyRV(boolean isEmpty) {
				requireView().findViewById(R.id.food_rv_empty_text)
						.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
			}
		});

		foodRV.setLayoutManager(new LinearLayoutManager(getActivity()));
		viewModel.getAllFoods().observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
			@Override public void onChanged(List<Food> foods) {
				adapter.setData(foods);
			}
		});

		foodRV.setAdapter(adapter);

		// Set up the floating action button for adding new foods
		addFoodFAB = view.findViewById(R.id.add_food_fab);
		addFoodFAB.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				Toast.makeText(requireContext(),
						"Adding an random food for testing",
						Toast.LENGTH_SHORT).show();
				viewModel.insert(Food.makeRandom());
			}
		});
	}
}