package com.fitnesstracker.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import com.fitnesstracker.ui.Application;
import com.fitnesstracker.ui.notifications.NotificationPublisher;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * An {@link android.app.Activity} for adding {@link FoodDiaryEntry} objects to the database and
 * editing existing ones.
 * <p>
 * A new {@link FoodDiaryEntry} object can be created by starting this activity with no extras.
 * <p>
 * A {@link FoodDiaryEntry} object can be edited by passing it's ID as an extra using the key {@link
 * AddMealActivity#KEY_EDIT_ID}.
 *
 * @author Mitchell Ford
 */
public class AddMealActivity extends AppCompatActivity {

	/**
	 * Tag for {@link Log} messages.
	 */
	private static final String TAG = "AddMealActivity";

	/**
	 * The key of the extra that contains the id of the {@link FoodDiaryEntry} to edit.
	 */
	public static final String KEY_EDIT_ID = "diary_entry_id";

	/**
	 * The {@link FTViewModel} this activity uses to access the database.
	 */
	private FTViewModel viewModel;

	/**
	 * The button for saving your changes to the database.
	 * <p>
	 * This button will be deactivated while data is being retrieved from the database.
	 */
	private Button submitButton;

	/**
	 * The {@link Spinner} for selecting which food was eaten in this meal.
	 */
	private Spinner foodSpinner;

	/**
	 * The {@link EditText} for specifying the number of servings of food eaten in this meal.
	 * <p>
	 * Some error checking is done before processing the contents of this field, but it is expected
	 * that the operating system only allows properly formatted numbers greater than or equal to
	 * zero.
	 */
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

		// Retrieve a ViewModel to use for accessing the database
		viewModel = ViewModelProviders.of(this).get(FTViewModel.class);

		// Set up the back button to finish the activity without committing any changes
		Button backButton = findViewById(R.id.backButton);
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				// Finish the activity
				finish();
			}
		});

		// Initialize the edit text for the number of servings
		numServingsEditText = findViewById(R.id.numServingsEditText);

		// Set up the food spinner
		foodSpinner = findViewById(R.id.foodSpinner);

		// Set up the food spinner's adapter
		final FoodSpinnerAdapter adapter = new FoodSpinnerAdapter(this);
		foodSpinner.setAdapter(adapter);

		// Set up the spinner adapter to update it's data on changes to the database
		viewModel.getFoods().observe(this, new Observer<List<Food>>() {
			@Override public void onChanged(List<Food> foods) {
				adapter.setData(foods);
			}
		});

		// Set up the submit button
		submitButton = findViewById(R.id.submitButton);
		submitButton.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				submit();
			}
		});

		// Get the ID of the food diary entry being edited (or -1 if there isn't one)
		editId = getIntent().getLongExtra(KEY_EDIT_ID, -1);
		Log.d(TAG, "editId: " + editId);

		// If an ID is given for editing, fill in the form fields with
		// the values of the corresponding database entry
		if (editId != -1) {
			setUpForEditing();
		}
	}

	/**
	 * Fill in the form fields with data from the {@link FoodDiaryEntry} with ID {@link
	 * AddMealActivity#editId}.
	 * <p>
	 * This method should be called when a diary entry is being edited, after all UI elements have
	 * been initialized.
	 */
	private void setUpForEditing() {

		// Disable the submit button until the database
		// provides data on the diary entry being edited
		submitButton.setEnabled(false);

		// Get data on the diary entry being edited from the database
		// and update the UI elements
		viewModel.setMealSearchKeyId(editId);
		viewModel.getMealById().observe(this, new Observer<Meal>() {
			@Override public void onChanged(Meal meal) {

				// Set the selected item in the spinner
				setFoodSpinnerSelected(meal.getFood());

				// Set the number of servings
				numServingsEditText.setText(String.format(
						Locale.getDefault(),
						"%f",
						meal.getFoodDiaryEntry().getNumServings()));

				// Enable the submit button now that the database has provided data
				submitButton.setEnabled(true);
			}
		});
	}

	/**
	 * Set the selected object in {@link AddMealActivity#foodSpinner} by value.
	 *
	 * @param food the Food object that should be selected.
	 */
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

	/**
	 * Commit the changes made by the user to the database.
	 * <p>
	 * This method handles error-checking the user's data and displays an error message as a {@link
	 * Toast} in the case of an error.
	 */
	private void submit() {
		Log.d(TAG, "submit()");

		// Get the selected food
		Food food = (Food) foodSpinner.getSelectedItem();

		// Ensure that a food was actually selected
		if (food == null) {
			Toast.makeText(this,
					"You must select a food.",
					Toast.LENGTH_SHORT).show();
			return;
		}

		// Get the number of servings as a string
		String numServingsText = numServingsEditText.getText().toString().trim();

		// Ensure that the edit text field wasn't left empty
		if (numServingsText.length() == 0) {
			Toast.makeText(this,
					"You must specify a number of servings.",
					Toast.LENGTH_SHORT).show();
			return;
		}

		// Get the number of servings as a double
		Double numServings = null;
		try {
			numServings = Double.parseDouble(numServingsText);
		} catch (NumberFormatException ignored) {
		}

		// Ensure that a number could be parsed and that it is positive if it was
		if (numServings == null || numServings <= 0) {
			Toast.makeText(this,
					"You specify a number of servings greater than zero.",
					Toast.LENGTH_SHORT).show();
			return;
		}

		// Commit these changes to the database
		if (editId == -1) {
			// Create a new database entry
			viewModel.insert(new FoodDiaryEntry(food, numServings, System.currentTimeMillis()));
		} else {
			// Edit an existing database entry
			viewModel.update(new FoodDiaryEntry(editId, food.getId(), numServings, System.currentTimeMillis()));
		}

		// Because the user has interacted with the app today, push the reminder notification
		// back to tomorrow
		scheduleDailyReminderNotification();

		// Finish the activity
		finish();
	}

	/**
	 * Schedule a daily reminder to add a diary entry.
	 */
	private void scheduleDailyReminderNotification() {
		Log.d(TAG, "scheduleDailyReminderNotification()");

		// Get a calendar instance for 8pm tomorrow
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 20);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		// Push it back another day if the current reminder time has passed
		if (Calendar.getInstance().after(calendar)) {
			calendar.add(Calendar.DATE, 1);
		}

		// TODO: make it so tapping the notification opens this activity
		// Create a notification
		Notification notification = new Notification.Builder(this, Application.REMINDER_CHANNEL_ID)
				.setSmallIcon(R.mipmap.ic_launcher_round)
				.setContentTitle("Fitness Tracker Daily Reminder")
				.setContentText("It looks like you haven't logged any meals today.")
				.setAutoCancel(true)
				.build();

		// Get the ID of this notification
		int notificationId = NotificationPublisher.DAILY_REMINDER_NOTIFICATION_ID;

		// Create an intent that holds the notification and it's ID
		Intent notificationIntent = new Intent(this, NotificationPublisher.class);
		notificationIntent.putExtra(NotificationPublisher.KEY_NOTIFICATION, notification);
		notificationIntent.putExtra(NotificationPublisher.KEY_NOTIFICATION_ID, notificationId);

		// Create a pending intent for displaying the notification
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				this,
				NotificationPublisher.DAILY_REMINDER_NOTIFICATION_ID,
				notificationIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		// Set up the alarm manager system service to display this notification daily
		AlarmManager alarmManager = getSystemService(AlarmManager.class);
		alarmManager.setRepeating(
				AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis(),
				AlarmManager.INTERVAL_DAY,
				pendingIntent);
	}

	/**
	 * An {@link ArrayAdapter} for displaying {@link Food} objects in a {@link Spinner}.
	 */
	static class FoodSpinnerAdapter extends ArrayAdapter<Food> {

		/**
		 * Constructor that specifies only a context.
		 * <p>
		 * Passes <code>0</code> as a resource ID.
		 *
		 * @param context the context this adapter is being created in
		 *
		 * @see FoodSpinnerAdapter#FoodSpinnerAdapter(Context, int)
		 */
		public FoodSpinnerAdapter(@NonNull Context context) {
			this(context, 0);
		}

		/**
		 * Constructor that specifies a context and a resource id.
		 *
		 * @param context  the context this adapter is being created in
		 * @param resource this value is unused in this implementation
		 */
		public FoodSpinnerAdapter(@NonNull Context context, int resource) {
			super(context, resource);
		}

		/**
		 * Set the data displayed in this spinner.
		 *
		 * @param data the new data to display
		 */
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

		/**
		 * Handles the initialization of views for {@link FoodSpinnerAdapter#getView(int, View,
		 * ViewGroup)} and {@link FoodSpinnerAdapter#getDropDownView(int, View, ViewGroup)}.
		 * <p>
		 * Attempts to reuse old views when possible.
		 *
		 * @param position    the position of the item to display
		 * @param convertView an old view that can be reused
		 * @param parent      the parent view group
		 *
		 * @return a new or reused view
		 */
		private View initView(int position, View convertView, ViewGroup parent) {

			// Reuse convertView if possible
			if (convertView == null) {
				convertView = LayoutInflater
						.from(getContext())
						.inflate(R.layout.food_view, parent, false);
			}

			// Get the food item to display
			Food food = getItem(position);

			// Fill in the name of the food
			TextView nameTextView = (TextView) convertView.findViewById(R.id.foodName);
			nameTextView.setText(food.getName());

			// Fill in the serving info of the food
			TextView servingInfoTextView = (TextView) convertView.findViewById(R.id.servingInfo);
			servingInfoTextView.setText(String.format(Locale.getDefault(),
					"%.2f %s",
					food.getServingSize(),
					food.getServingUnit())
			);

			// Return the view
			return convertView;
		}
	}
}