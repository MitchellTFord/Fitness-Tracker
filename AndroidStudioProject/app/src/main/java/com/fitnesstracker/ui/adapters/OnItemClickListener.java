package com.fitnesstracker.ui.adapters;

/**
 * An interface for handling the clicking of items within a {@link androidx.recyclerview.widget.RecyclerView}.
 *
 * @param <T> the type of the data being displayed in the recyclerview
 */
public interface OnItemClickListener<T> {

	/**
	 * Handle the clicking of items in a {@link androidx.recyclerview.widget.RecyclerView}.
	 *
	 * @param item the item that was clicked
	 */
	void onItemClicked(T item);

	/**
	 * Handle the long-clicking of items in a {@link androidx.recyclerview.widget.RecyclerView}.
	 *
	 * @param item the item that was long-clicked
	 */
	void onItemLongClicked(T item);
}
