package com.fitnesstracker.ui.adapters;

/**
 * An interface for handling the clicking of items within a {@link androidx.recyclerview.widget.RecyclerView}.
 *
 * @param <T> the type of the data being displayed in the recyclerview
 */
public interface OnItemClickListener<T> {
	void onItemClicked(T item);

	void onItemLongClicked(T item);
}
