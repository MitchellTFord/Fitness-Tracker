package com.fitnesstracker.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.fitnesstracker.R;
import com.fitnesstracker.database.DiaryEntry;
import com.fitnesstracker.database.FTViewModel;
import com.fitnesstracker.database.FoodServingTuple;

import java.util.List;
import java.util.Locale;

public class DiaryEntryFoodAdapter extends RecyclerView.Adapter<DiaryEntryFoodAdapter.ViewHolder> {

	private final DiaryEntry diaryEntry;
	private List<FoodServingTuple> data;
	private final FTViewModel viewModel;

	public DiaryEntryFoodAdapter(DiaryEntry diaryEntry, FTViewModel viewModel) {
		this.diaryEntry = diaryEntry;
		this.viewModel = viewModel;
	}

	@NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		LayoutInflater inflater = LayoutInflater.from(context);

		View foodServingTupleView = inflater.inflate(R.layout.diary_entry_food_view, parent, false);

		viewModel.getMealsFromDiary(diaryEntry).observeForever(new Observer<List<FoodServingTuple>>() {
			@Override
			public void onChanged(List<FoodServingTuple> data) {
				setData(data);
			}
		});

		ViewHolder viewHolder = new ViewHolder(foodServingTupleView);
		return viewHolder;
	}

	@Override public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
		FoodServingTuple tuple = data.get(position);
		holder.foodInfoText.setText(String.format(Locale.getDefault(),
				"%.2f %s %s",
				tuple.getNumServings() * tuple.getFood().getServingSize(),
				tuple.getFood().getServingUnit(),
				tuple.getFood().getName()
		));
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	public void setData(final List<FoodServingTuple> data) {
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
