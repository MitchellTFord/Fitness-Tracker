package com.fitnesstracker.ui.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.fitnesstracker.R;
import com.fitnesstracker.database.FTViewModel;
import com.fitnesstracker.database.Meal;
import com.fitnesstracker.ui.activities.AddMealActivity;
import com.fitnesstracker.ui.adapters.DiaryEntryAdapter;
import com.fitnesstracker.ui.adapters.OnItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass. Use the {@link DiaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiaryFragment extends Fragment {

	private FTViewModel viewModel;

	private FloatingActionButton addDiaryEntryFAB;

	private Button dateButton;
	private final Calendar calendar = Calendar.getInstance();

	public DiaryFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of this fragment using the provided
	 * parameters.
	 *
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

		// Set up the calendar
		calendar.setTime(new Date(System.currentTimeMillis()));

		// Listener that updates the date button when the user picks a date
		final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
			@Override public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
				calendar.set(year, month, dayOfMonth);
				updateDate();

			}
		};

		// Set up the date button
		dateButton = view.findViewById(R.id.date_button);
		dateButton.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				new DatePickerDialog(
						getContext(),
						onDateSetListener,
						calendar.get(Calendar.YEAR),
						calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH)
				).show();
			}
		});
		updateDate();

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

		final TextView noDataTextView = requireView().findViewById(R.id.diary_rv_empty_text);

		// Observe database changes
		viewModel.getMealsByTime().observe(getViewLifecycleOwner(), new Observer<List<Meal>>() {
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
		intent.putExtra(AddMealActivity.EDIT_ID_EXTRA_KEY, meal.getFoodDiaryEntry().getId());
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

	private void updateDate() {
		viewModel.setMealSearchKeyTime(calendar.getTimeInMillis());
		dateButton.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
	}
}