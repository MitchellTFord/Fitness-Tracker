package com.fitnesstracker.database.daos;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.fitnesstracker.database.FTDatabase;
import com.fitnesstracker.database.Food;
import com.fitnesstracker.database.FoodDiaryEntry;
import com.fitnesstracker.database.Meal;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

/**
 * A generic data access object to be used with {@link FTDatabase}.
 *
 * @author Mitchell Ford
 */
@Dao
public abstract class FTDao<T> {

	/**
	 * Insert one or more objects into the database.
	 *
	 * @param items one or more items to insert
	 *
	 * @return the number of items that were inserted
	 */
	@Insert
	public abstract void insert(T... items);

	/**
	 * Update one or more items in the database.
	 *
	 * @param items one or more items to update
	 *
	 * @return the number of items that were updated
	 */
	@Update
	public abstract int update(T... items);

	/**
	 * Delete one or more items in the database.
	 *
	 * @param items one or more items to delete
	 */
	@Delete
	public abstract int delete(T... items);
}
