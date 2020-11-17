package com.fitnesstracker.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fitnesstracker.R;
import com.fitnesstracker.database.FTViewModel;
import com.fitnesstracker.database.Meal;
import com.fitnesstracker.database.entities.Food;
import com.fitnesstracker.database.entities.FoodDiaryEntry;

import java.util.List;
import java.util.Locale;

public class AddMealActivity extends AppCompatActivity {

	private static final String TAG = "AddMealActivity";

	/**
	 * The key of the extra that contains the id of the {@link FoodDiaryEntry} to edit.
	 */
	public static final String EDIT_ID_EXTRA_KEY = "diary_entry_id";

	private FTViewModel viewModel;

	private Button submitButton;
	private Spinner foodSpinner;
	private EditText numServingsEditText;

	/**
	 * If this activity is being used to edit a {@link FoodDiaryEntry}, this is the id of entry
	 * being edited, -1 otherwise.
	 */
	private long editId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_meal);

		viewModel = ViewModelProviders.of(this).get(FTViewModel.class);

		Button backButton = findViewById(R.id.backButton);
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				// Finish the activity
				finish();
			}
		});

		foodSpinner = findViewById(R.id.foodSpinner);

		final FoodSpinnerAdapter adapter = new FoodSpinnerAdapter(this, 0);
		foodSpinner.setAdapter(adapter);

		viewModel.getFoods().observe(this, new Observer<List<Food>>() {
			@Override public void onChanged(List<Food> foods) {
				adapter.setData(foods);
			}
		});

		numServingsEditText = findViewById(R.id.numServingsEditText);

		submitButton = findViewById(R.id.submitButton);
		submitButton.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				submit();
			}
		});

		editId = getIntent().getLongExtra(EDIT_ID_EXTRA_KEY, -1);
		Log.d(TAG, "editId: " + editId);
		if (editId != -1) {
			setUpForEditing();
		}
	}

	private void setUpForEditing() {
		submitButton.setEnabled(false);

		viewModel.setMealSearchKeyId(editId);
		viewModel.getMealById().observe(this, new Observer<Meal>() {
			@Override public void onChanged(Meal meal) {
				setFoodSpinnerSelected(meal.getFood());
				numServingsEditText.setText(String.format(
						Locale.getDefault(),
						"%f",
						meal.getFoodDiaryEntry().getNumServings()));

				submitButton.setEnabled(true);
			}
		});
	}

	private void setFoodSpinnerSelected(Food food) {
		FoodSpinnerAdapter adapter = (FoodSpinnerAdapter) foodSpinner.getAdapter();
		int count = adapter.getCount();
		for (int i = 0; i < count; i++) {
			Food item = adapter.getItem(i);
			if (food.equals(item)) {
				foodSpinner.setSelection(i, false);
				return;
			}
		}
	}

	private void submit() {
		Food food = (Food) foodSpinner.getSelectedItem();

		if (food == null) {
			Toast.makeText(this,
					"You must select a food.",
					Toast.LENGTH_SHORT).show();
			return;
		}

		String numServingsText = numServingsEditText.getText().toString().trim();

		if (numServingsText.length() == 0) {
			Toast.makeText(this,
					"You must specify a number of servings.",
					Toast.LENGTH_SHORT).show();
			return;
		}

		Double numServings = null;
		try {
			numServings = Double.parseDouble(numServingsText);
		} catch (NumberFormatException ignored) {
		}


		if (numServings == null || numServings <= 0) {
			Toast.makeText(this,
					"You specify a number of servings greater than zero.",
					Toast.LENGTH_SHORT).show();
			return;
		}

		if(editId == -1) {
			viewModel.insert(new FoodDiaryEntry(food, numServings, System.currentTimeMillis()));
		} else {
			viewModel.update(new FoodDiaryEntry(editId, food.getId(), numServings, System.currentTimeMillis()));
		}

		finish();
	}

	static class FoodSpinnerAdapter extends ArrayAdapter<Food> {

		public FoodSpinnerAdapter(@NonNull Context context, int resource) {
			super(context, resource);
		}

		public void setData(List<Food> data) {
			clear();
			addAll(data);
			notifyDataSetChanged();
		}

		@NonNull
		@Override
		public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
			return initView(position, convertView, parent);
		}

		@Override
		public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
			return initView(position, convertView, parent);
		}

		private View initView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater
						.from(getContext())
						.inflate(R.layout.food_view, parent, false);
			}

			Food food = getItem(position);

			TextView nameTextView = (TextView) convertView.findViewById(R.id.foodName);
			nameTextView.setText(food.getName());

			TextView servingInfoTextView = (TextView) convertView.findViewById(R.id.servingInfo);
			servingInfoTextView.setText(String.format(Locale.getDefault(),
					"%.2f %s",
					food.getServingSize(),
					food.getServingUnit())
			);

			return convertView;
		}
	}
}