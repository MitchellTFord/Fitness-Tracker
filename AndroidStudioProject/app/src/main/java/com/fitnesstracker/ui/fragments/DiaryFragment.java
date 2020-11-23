package com.fitnesstracker.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.fitnesstracker.R;
import com.fitnesstracker.database.FTViewModel;
import com.fitnesstracker.database.Meal;
import com.fitnesstracker.database.entities.Food;
import com.fitnesstracker.ui.activities.AddMealActivity;
import com.fitnesstracker.ui.adapters.DiaryEntryAdapter;
import com.fitnesstracker.ui.adapters.OnItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A fragment where the user can view, edit, and delete {@link com.fitnesstracker.database.entities.FoodDiaryEntry}
 * objects in the database.
 */
public class DiaryFragment extends Fragment {

	/**
	 * The view model that this fragment uses to interact with the database.
	 */
	private FTViewModel viewModel;

	/**
	 * The floating action button for adding a new diary entry.
	 */
	private FloatingActionButton addDiaryEntryFAB;

	/**
	 * Required empty public constructor.
	 */
	public DiaryFragment() {
	}

	/**
	 * A factory method that creates a new instance of this fragment.
	 *
	 * @return A new instance of DiaryFragment
	 */
	public static DiaryFragment newInstance() {
		return new DiaryFragment();
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

		// Set up the RecyclerView adapter with an OnItemClickListener
		final DiaryEntryAdapter adapter = new DiaryEntryAdapter(new OnItemClickListener<Meal>() {
			@Override public void onItemClicked(Meal item) {
				editMeal(item);
			}

			// Display a dialog that allows the user to edit or delete this item
			@Override public void onItemLongClicked(final Meal item) {
				new AlertDialog.Builder(getActivity())
						.setTitle(R.string.item_long_click_dialog_title)
						.setItems(R.array.item_long_click_dialog_options, new DialogInterface.OnClickListener() {
							@Override public void onClick(DialogInterface dialog, int which) {
								if (which == 0) {
									editMeal(item);
								} else if (which == 1) {
									deleteMeal(item);
								}
							}
						})
						.show();
			}
		});
		rv.setAdapter(adapter);

		// Set up the text view that displays when the recycler view has no data to display
		final TextView noDataTextView = requireView().findViewById(R.id.diary_rv_empty_text);

		// Observe database changes and update the recycler view and the no-data text view
		viewModel.getMeals().observe(getViewLifecycleOwner(), new Observer<List<Meal>>() {
			@Override public void onChanged(List<Meal> meals) {
				adapter.setData(meals);
				noDataTextView.setVisibility(meals == null || meals.isEmpty() ? View.VISIBLE : View.GONE);
			}
		});

		// Set up the floating action button for adding new foods
		addDiaryEntryFAB = view.findViewById(R.id.add_diary_entry_fab);
		addDiaryEntryFAB.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				addMeal();
			}
		});
	}

	/**
	 * Open the activity for adding a new {@link com.fitnesstracker.database.entities.FoodDiaryEntry}.
	 */
	private void addMeal() {
		Intent intent = new Intent(getContext(), AddMealActivity.class);
		startActivity(intent);
	}

	/**
	 * Open the activity for editing a meal's backing {@link com.fitnesstracker.database.entities.FoodDiaryEntry}.
	 *
	 * @param meal the meal whose diary entry should be edited
	 */
	private void editMeal(Meal meal) {
		Intent intent = new Intent(getContext(), AddMealActivity.class);
		intent.putExtra(AddMealActivity.KEY_EDIT_ID, meal.getFoodDiaryEntry().getId());
		startActivity(intent);
	}

	/**
	 * Delete this meal's diary entry from the database.
	 *
	 * @param meal the meal whose diary entry should be deleted
	 */
	private void deleteMeal(Meal meal) {
		viewModel.delete(meal.getFoodDiaryEntry());
	}
}