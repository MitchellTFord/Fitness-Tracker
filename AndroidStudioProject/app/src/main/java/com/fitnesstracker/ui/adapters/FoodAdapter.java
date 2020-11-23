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
import com.fitnesstracker.database.entities.Food;

import java.util.List;
import java.util.Locale;

/**
 * A {@link RecyclerView.Adapter} for {@link Food} objects.
 *
 * @author Mitchell Ford
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

	/**
	 * The data being displayed in recycler views using this adapter.
	 */
	private List<Food> data;

	/**
	 * The listener for clicks and long-clicks on view holders in this adapter.
	 * <p>
	 * This field may be null.
	 * <p>
	 * This field must be specified in a constructor.
	 */
	@Nullable
	private final OnItemClickListener<Food> onItemClickListener;

	/**
	 * Constructor that does not specify {@link FoodAdapter#onItemClickListener}.
	 */
	public FoodAdapter() {
		this(null);
	}

	/**
	 * Constructor that specifies {@link FoodAdapter#onItemClickListener}.
	 *
	 * @param onItemClickListener the listener that should be used for clicks and long-clicks in *
	 *                            this adapter
	 */
	public FoodAdapter(@Nullable OnItemClickListener<Food> onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		LayoutInflater inflater = LayoutInflater.from(context);

		// Inflate the view holder layout
		View foodView = inflater.inflate(R.layout.food_view, parent, false);

		return new ViewHolder(foodView);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

		// Get the food item from data
		Food food = data.get(position);

		// Set up the name text view
		TextView nameTextView = holder.nameTextView;
		nameTextView.setText(food.getName());

		// Set up the serving info text view
		TextView servingInfoTextView = holder.servingInfoTextView;
		servingInfoTextView.setText(String.format(Locale.getDefault(),
				"%.2f %s",
				food.getServingSize(),
				food.getServingUnit())
		);

		// Bind the onItemClickListener if one was given in the constructor
		if (onItemClickListener != null) {
			holder.bindOnItemClickListener(food, onItemClickListener);
		}
	}

	/**
	 * Get the total number of items in this adapter.
	 * <p>
	 * If {@link FoodAdapter#data} is <code>null</code>, the item count is considered
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
	public void setData(List<Food> data) {
		this.data = data;

		// This is a very inefficient way of handling data changes
		notifyDataSetChanged();
	}

	/**
	 * A view holder for {@link Food} objects.
	 */
	public static class ViewHolder extends RecyclerView.ViewHolder {

		/**
		 * Text view that displays the name of this food.
		 */
		public TextView nameTextView;

		/**
		 * Text view that displays this foods serving size and serving unit.
		 */
		public TextView servingInfoTextView;

		/**
		 * Constructor that initializes <code>nameTextView</code> and <code>servingInfoTextView</code>.
		 *
		 * @param itemView the view that
		 */
		public ViewHolder(@NonNull View itemView) {
			super(itemView);

			nameTextView = (TextView) itemView.findViewById(R.id.foodName);
			servingInfoTextView = (TextView) itemView.findViewById(R.id.servingInfo);
		}

		/**
		 * Bind this {@link DiaryEntryAdapter.ViewHolder} to an {@link OnItemClickListener} to
		 * handle click events.
		 *
		 * @param food                the {@link Food} object held by this view holder
		 * @param onItemClickListener the {@link OnItemClickListener} that should handle click
		 *                            events for this view holder
		 */
		public void bindOnItemClickListener(final Food food, final OnItemClickListener<Food> onItemClickListener) {

			// Set the click listener for this item view
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override public void onClick(View v) {
					onItemClickListener.onItemClicked(food);
				}
			});

			// Set the long-click listener for this item view
			itemView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override public boolean onLongClick(View v) {
					onItemClickListener.onItemLongClicked(food);
					return true;
				}
			});
		}
	}
}
