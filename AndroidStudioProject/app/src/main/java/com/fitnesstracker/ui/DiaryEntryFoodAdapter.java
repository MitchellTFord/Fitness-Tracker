package com.fitnesstracker.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitnesstracker.R;
import com.fitnesstracker.database.DiaryEntry;
import com.fitnesstracker.database.FoodServingTuple;

import java.util.List;
import java.util.Locale;

public class DiaryEntryFoodAdapter extends RecyclerView.Adapter<DiaryEntryFoodAdapter.ViewHolder> {

	private DiaryEntry diaryEntry;
	private List<FoodServingTuple> data;

	public DiaryEntryFoodAdapter(DiaryEntry diaryEntry) {
		this.diaryEntry = diaryEntry;
	}

	@NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		LayoutInflater inflater = LayoutInflater.from(context);

		View foodServingTupleView = inflater.inflate(R.layout.diary_entry_food_view, parent, false);

		ViewHolder viewHolder = new ViewHolder(foodServingTupleView);
		return viewHolder;
	}

	@Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		FoodServingTuple tuple = data.get(position);

		 holder.foodInfoText.setText(String.format(Locale.getDefault(),
		 		"%.2f %s %s",
				 tuple.getNumServings() * tuple.getFood().getServingSize(),
				 tuple.getFood().getServingUnit(),
				 tuple.getFood().getName()
		 ));
	}

	@Override public int getItemCount() {
		return data.size();
	}

	public void setData(List<FoodServingTuple> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView foodInfoText;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);

			foodInfoText = (TextView) itemView.findViewById(R.id.food_info_text);
		}
	}
}
