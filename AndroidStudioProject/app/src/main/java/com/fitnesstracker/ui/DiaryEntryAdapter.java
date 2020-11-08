package com.fitnesstracker.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitnesstracker.R;
import com.fitnesstracker.database.DiaryEntry;
import com.fitnesstracker.database.FTViewModel;
import com.fitnesstracker.database.FoodServingTuple;

import java.util.ArrayList;
import java.util.List;

public class DiaryEntryAdapter extends RecyclerView.Adapter<DiaryEntryAdapter.ViewHolder> {

	private final FTViewModel viewModel;

	private EmptyRVHandler emptyRVHandler;

	private List<DiaryEntry> data = new ArrayList<>();

	public DiaryEntryAdapter(FTViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		LayoutInflater inflater = LayoutInflater.from(context);

		View diaryEntryView = inflater.inflate(R.layout.diary_entry_view, parent, false);

		ViewHolder viewHolder = new ViewHolder(diaryEntryView);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		DiaryEntry diaryEntry = data.get(position);

		RecyclerView rv = holder.rv;
		rv.setLayoutManager(new LinearLayoutManager(holder.rv.getContext()));
		final DiaryEntryFoodAdapter adapter = new DiaryEntryFoodAdapter(diaryEntry, viewModel);
		rv.setLayoutManager(new LinearLayoutManager(holder.rv.getContext()));
		rv.setAdapter(adapter);
		viewModel.getMealsFromDiary(diaryEntry).observeForever(new Observer<List<FoodServingTuple>>() {
			@Override public void onChanged(List<FoodServingTuple> foodServingTuples) {
				adapter.setData(foodServingTuples);
			}
		});
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	public void setData(List<DiaryEntry> data) {
		this.data = data;
		handleEmpty();
		notifyDataSetChanged();
	}

	public void handleEmpty() {
		if (emptyRVHandler != null) {
			emptyRVHandler.handleEmptyRV(data == null || data.isEmpty());
		}
	}

	public void setEmptyRVHandler(EmptyRVHandler emptyRVHandler) {
		this.emptyRVHandler = emptyRVHandler;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView headerText;
		public RecyclerView rv;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);

			headerText = itemView.findViewById(R.id.diary_entry_header_text);
			rv = itemView.findViewById(R.id.food_diary_entries);
		}
	}
}
