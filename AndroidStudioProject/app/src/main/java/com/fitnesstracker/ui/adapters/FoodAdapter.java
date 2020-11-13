package com.fitnesstracker.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitnesstracker.R;
import com.fitnesstracker.database.entities.Food;

import java.util.List;
import java.util.Locale;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

	private List<Food> data;

	public FoodAdapter(List<Food> data) {
		setData(data);
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
	}

	@Override
	public int getItemCount() {
		return data.size();
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
	}
}
