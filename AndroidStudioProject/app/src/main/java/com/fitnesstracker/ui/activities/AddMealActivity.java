package com.fitnesstracker.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;
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
import com.fitnesstracker.database.entities.Food;
import com.fitnesstracker.database.entities.FoodDiaryEntry;

import java.util.List;
import java.util.Locale;

public class AddMealActivity extends AppCompatActivity {

	private FTViewModel viewModel;

	private Spinner foodSpinner;
	private EditText numServingsEditText;

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

		Button submitButton = findViewById(R.id.submitButton);
		submitButton.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				submit();
			}
		});
	}

	private void submit() {
		Food food = (Food) foodSpinner.getSelectedItem();
		double numServings = Double.parseDouble(numServingsEditText.getText().toString());

		if(food == null) {
			Toast.makeText(this,
					"You must select a food.",
					Toast.LENGTH_SHORT).show();
			return;
		}

		if(numServings <= 0) {
			Toast.makeText(this,
					"You specify a number of servings greater than zero.",
					Toast.LENGTH_SHORT).show();
			return;
		}

		viewModel.insert(new FoodDiaryEntry(food, numServings, System.currentTimeMillis()));

		Toast.makeText(this,
				"Successfully created the diary entry.",
				Toast.LENGTH_SHORT).show();

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
			if(convertView == null) {
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