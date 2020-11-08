package com.fitnesstracker.ui;

import androidx.recyclerview.widget.RecyclerView;

/**
 * A functional interface intended to be called in conjunction with
 * {@link RecyclerView.Adapter#notifyDataSetChanged()} to handle behaviours that should
 * occur when a {@link RecyclerView} becomes empty or no longer empty.
 */
@FunctionalInterface
public interface EmptyRVHandler {
	/**
	 * Should be called whenever
	 * @param isEmpty whether a RecyclerView Adapter's dataset is empty
	 */
	public void handleEmptyRV(boolean isEmpty);
}
