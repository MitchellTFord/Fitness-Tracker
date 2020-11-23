package com.fitnesstracker.database.daos;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.fitnesstracker.database.entities.Food;

import java.util.List;

/**
 * A data access object for {@link Food} entities to be used with {@link
 * com.fitnesstracker.database.FTDatabase}.
 * <p>
 * For every method there is a {@link LiveData}-returning method with the same name except for
 * having "LD" at the end. For example, if you wish to observe the results of {@link
 * FoodDao#getAll()}, use {@link FoodDao#getAllLD()}.
 */
@Dao
public abstract class FoodDao extends FTDao<Food> {

	/**
	 * Get a list of all foods.
	 *
	 * @return a list of all foods
	 */
	@Query("SELECT * FROM food")
	public abstract List<Food> getAll();

	/**
	 * Get an observable list of all foods.
	 *
	 * @return an observable list of all foods
	 */
	@Query("SELECT * FROM food")
	public abstract LiveData<List<Food>> getAllLD();

	/**
	 * Get a food by ID.
	 * <p>
	 * This method will return null if there is no food with the given ID.
	 *
	 * @param id the ID to search for
	 *
	 * @return a food with a matching ID or null if there is none
	 */
	@Nullable
	@Query("SELECT * FROM food WHERE id = :id")
	public abstract Food get(long id);

	/**
	 * Get an observable food by ID.
	 * <p>
	 * This method will not return null, but the returned {@link LiveData} may contain null.
	 *
	 * @param id the ID to search for
	 *
	 * @return an observable food with a matching ID
	 */
	@Query("SELECT * FROM food WHERE id = :id")
	public abstract LiveData<Food> getLD(long id);

	/**
	 * Get a list of foods by name.
	 *
	 * @param name the name to search for
	 *
	 * @return a list of foods a the matching name
	 */
	@Query("SELECT * FROM food WHERE name LIKE :name")
	public abstract List<Food> get(String name);

	/**
	 * Get an observable list of foods by name.
	 *
	 * @param name the name to search for
	 *
	 * @return an observable list of foods by name
	 */
	@Query("SELECT * FROM food WHERE name LIKE :name")
	public abstract LiveData<List<Food>> getLD(String name);

	/**
	 * Get the number of foods.
	 *
	 * @return the number of foods
	 */
	@Query("SELECT COUNT(id) FROM food")
	public abstract Integer getCount();

	/**
	 * Get an observable number of foods
	 *
	 * @return an observable number of food
	 */
	@Query("SELECT COUNT(id) FROM food")
	public abstract LiveData<Integer> getCountLD();
}
