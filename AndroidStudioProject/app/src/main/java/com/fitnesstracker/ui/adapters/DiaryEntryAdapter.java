package com.fitnesstracker.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.fitnesstracker.R;
import com.fitnesstracker.database.Meal;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A {@link RecyclerView.Adapter} for {@link DiaryEntryAdapter} objects.
 *
 * @author Mitchell Ford
 */
public class DiaryEntryAdapter extends RecyclerView.Adapter<DiaryEntryAdapter.ViewHolder> {

	/**
	 * The data that the {@link RecyclerView} should display.
	 */
	@Nullable
	private List<Meal> data = new ArrayList<>();

	/**
	 * The listener for clicks and long-clicks on view holders in this adapter.
	 * <p>
	 * This field may be null.
	 * <p>
	 * This field must be specified in a constructor.
	 */
	@Nullable
	private final OnItemClickListener<Meal> onItemClickListener;

	/**
	 * Default constructor that does not specify {@link DiaryEntryAdapter onItemClickListener}.
	 */
	public DiaryEntryAdapter() {
		this(null);
	}

	/**
	 * Constructor that specifies {@link DiaryEntryAdapter#onItemClickListener}.
	 *
	 * @param onItemClickListener the listener that should be used for clicks and long-clicks in
	 *                            this adapter
	 */
	public DiaryEntryAdapter(@Nullable OnItemClickListener<Meal> onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		LayoutInflater inflater = LayoutInflater.from(context);

		View diaryEntryView = inflater.inflate(R.layout.meal_view, parent, false);

		return new ViewHolder(diaryEntryView);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

		// Get the meal item from data
		assert data != null;
		Meal meal = data.get(position);

		// Set up the food info text view
		holder.foodInfoText.setText(String.format(Locale.getDefault(),
				"%.2f %s %s",
				meal.getFoodDiaryEntry().getNumServings()*meal.getFood().getServingSize(),
				meal.getFood().getServingUnit(),
				meal.getFood().getName()
		));

		// Set up the time text view
		holder.timeInfoText.setText(DateFormat
				.getTimeInstance(DateFormat.SHORT)
				.format(new Date(meal.getFoodDiaryEntry().getTime())));

		// Bind the onItemClickListener if one was given in the constructor
		if (onItemClickListener != null) {
			holder.bindOnItemClickListener(meal, onItemClickListener);
		}
	}

	/**
	 * Get the total number of items in this adapter.
	 * <p>
	 * If {@link DiaryEntryAdapter#data} is <code>null</code>, the item count is considered
	 * <code>0</code>.
	 *
	 * @return the total number of items in this adapter
	 */
	@Override
	public int getItemCount() {
		return data == null ? 0 : data.size();
	}

	/**
	 * Set the data in this adapter.
	 * <p>
	 * This method notifies of data changes using {@link RecyclerView.Adapter#notifyDataSetChanged()}.
	 *
	 * @param data the new data to set
	 */
	public void setData(@Nullable List<Meal> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	/**
	 * A view holder for {@link com.fitnesstracker.database.entities.FoodDiaryEntry} objects.
	 */
	public static class ViewHolder extends RecyclerView.ViewHolder {

		/**
		 * Text view that displays information about the food and number of servings thereof.
		 */
		public TextView foodInfoText;

		/**
		 * Text view that displays the time this diary entry took place.
		 */
		public TextView timeInfoText;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);

			foodInfoText = itemView.findViewById(R.id.food_info_text);
			timeInfoText = itemView.findViewById(R.id.time_info_text);
		}

		/**
		 * Bind this {@link ViewHolder} to an {@link OnItemClickListener} to handle click events.
		 *
		 * @param meal                the {@link Meal} object held by this view holder
		 * @param onItemClickListener the {@link OnItemClickListener} that should handle click
		 *                            events for this view holder
		 */
		public void bindOnItemClickListener(final Meal meal, final OnItemClickListener<Meal> onItemClickListener) {

			// Set the click listener for this item view
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override public void onClick(View v) {
					onItemClickListener.onItemClicked(meal);
				}
			});

			// Set the long-click listener for this item view
			itemView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override public boolean onLongClick(View v) {
					onItemClickListener.onItemLongClicked(meal);
					return true;
				}
			});
		}
	}
}
