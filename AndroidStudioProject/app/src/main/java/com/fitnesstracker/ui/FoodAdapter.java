package com.fitnesstracker.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitnesstracker.R;
import com.fitnesstracker.database.Food;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

	private List<Food> dataset;

	public FoodAdapter(List<Food> dataset) {
		setDataset(dataset);
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
		Food food = dataset.get(position);

		TextView nameTextView = holder.nameTextView;
		nameTextView.setText(food.getName());

		TextView servingInfoTextView = holder.servingInfoTextView;
		servingInfoTextView.setText(String.format("%.2f %s", food.getServingSize(), food.getServingUnit()));
	}

	@Override
	public int getItemCount() {
		return dataset.size();
	}

	public void setDataset(List<Food> dataset) {
		this.dataset = dataset;
		notifyDataSetChanged();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		public TextView nameTextView;
		public TextView servingInfoTextView;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);

			nameTextView = (TextView) itemView.findViewById(R.id.foodName);
			servingInfoTextView = (TextView) itemView.findViewById(R.id.servingInfo);
		}
	}
}