package com.fitnesstracker.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.fitnesstracker.database.FTDatabase;

import java.util.Collection;

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
	 */
	@Insert
	public abstract void insert(T... items);

	/**
	 * Insert one or more objects into the database.
	 *
	 * @param items one or more items to insert
	 */
	@Insert
	public abstract void insert(Collection<T> items);

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
