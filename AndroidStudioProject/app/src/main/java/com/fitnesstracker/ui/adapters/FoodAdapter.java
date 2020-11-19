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
import com.fitnesstracker.database.entities.Food;

import java.util.List;
import java.util.Locale;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

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

		View foodView = inflater.inflate(R.layout.food_view, parent, false);

		ViewHolder viewHolder = new ViewHolder(foodView);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		Food food = data.get(position);

		TextView nameTextView = holder.nameTextView;
		nameTextView.setText(food.getName());

		TextView servingInfoTextView = holder.servingInfoTextView;
		servingInfoTextView.setText(String.format(Locale.getDefault(),
				"%.2f %s",
				food.getServingSize(),
				food.getServingUnit())
		);

		// Notify onItemClickListener if it has been set
		if (onItemClickListener != null) {
			holder.bind(food, onItemClickListener);
		}
	}

	@Override
	public int getItemCount() {
		return data == null ? 0 : data.size();
	}

	public void setData(List<Food> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView nameTextView;
		public TextView servingInfoTextView;

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
		public void bind(final Food food, final OnItemClickListener<Food> onItemClickListener) {
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override public void onClick(View v) {
					onItemClickListener.onItemClicked(food);
				}
			});
			itemView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override public boolean onLongClick(View v) {
					onItemClickListener.onItemLongClicked(food);
					return true;
				}
			});
		}
	}
}
