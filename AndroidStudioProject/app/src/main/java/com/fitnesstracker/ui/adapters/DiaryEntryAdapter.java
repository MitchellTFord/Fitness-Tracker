package com.fitnesstracker.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitnesstracker.R;
import com.fitnesstracker.database.Meal;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DiaryEntryAdapter extends RecyclerView.Adapter<DiaryEntryAdapter.ViewHolder> {

	private List<Meal> data = new ArrayList<>();

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
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	public void setData(List<Meal> data) {
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
	}
}
