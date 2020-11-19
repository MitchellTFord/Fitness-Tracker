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
import com.fitnesstracker.database.entities.FoodDiaryEntry;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DiaryEntryAdapter extends RecyclerView.Adapter<DiaryEntryAdapter.ViewHolder> {

	/**
	 * The data that the {@link RecyclerView} should display.
	 */
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

		ViewHolder viewHolder = new ViewHolder(diaryEntryView);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		Meal meal = data.get(position);

		holder.foodInfoText.setText(String.format(Locale.getDefault(),
				"%.2f %s %s",
				meal.getFoodDiaryEntry().getNumServings()*meal.getFood().getServingSize(),
				meal.getFood().getServingUnit(),
				meal.getFood().getName()
		));

		holder.timeInfoText.setText(DateFormat
				.getTimeInstance(DateFormat.SHORT)
				.format(new Date(meal.getFoodDiaryEntry().getTime())));

		// Notify onItemClickListener if it has been set
		if (onItemClickListener != null) {
			holder.bind(meal, onItemClickListener);
		}
	}

	@Override
	public int getItemCount() {
		return data == null ? 0 : data.size();
	}

	public void setData(@Nullable List<Meal> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView foodInfoText;
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
		public void bind(final Meal meal, final OnItemClickListener<Meal> onItemClickListener) {
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override public void onClick(View v) {
					onItemClickListener.onItemClicked(meal);
				}
			});
			itemView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override public boolean onLongClick(View v) {
					onItemClickListener.onItemLongClicked(meal);
					return true;
				}
			});
		}
	}
}
