package com.fitnesstracker.database.daos;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.fitnesstracker.database.entities.FoodDiaryEntry;
import com.fitnesstracker.database.Meal;

import java.util.List;

/**
 * A data access object for {@link FoodDiaryEntry} entities to be used with {@link
 * com.fitnesstracker.database.FTDatabase}.
 * <p>
 * This class also contains methods for getting {@link Meal} objects from the database.
 * <p>
 * In some cases, it may be convenient to update a food diary entry indirectly using a meal. See
 * {@link FoodDiaryEntryDao#update(Meal...)}.
 * <p>
 * For every method there is a {@link LiveData}-returning method with the same name except for
 * having "LD" at the end. For example, if you wish to observe the results of {@link
 * FoodDiaryEntryDao#getAll()}, use {@link FoodDiaryEntryDao#getAllLD()}.
 */
@Dao
public abstract class FoodDiaryEntryDao extends FTDao<FoodDiaryEntry> {

	/**
	 * Get a list of all food diary entries.
	 * <p>
	 * To load accompanying {@link com.fitnesstracker.database.entities.Food} objects as well, use
	 * {@link FoodDiaryEntryDao#getAllMeals()}.
	 *
	 * @return a list of all food diary entries
	 */
	@Query("SELECT * FROM diary_food ORDER BY time DESC")
	public abstract List<FoodDiaryEntry> getAll();

	/**
	 * Get an observable list of all food diary entries.
	 * <p>
	 * To load accompanying {@link com.fitnesstracker.database.entities.Food} objects as well, use
	 * {@link FoodDiaryEntryDao#getAllMealsLD()}.
	 *
	 * @return and observable list of all food diary entries
	 */
	@Query("SELECT * FROM diary_food ORDER BY time DESC")
	public abstract LiveData<List<FoodDiaryEntry>> getAllLD();

	/**
	 * Get a food diary entry by ID.
	 * <p>
	 * This method will return null if there is no food diary entry with the given ID.
	 *
	 * @param id the ID to search for
	 *
	 * @return a food diary entry with a matching ID or null if there is none
	 */
	@Nullable
	@Query("SELECT * FROM diary_food WHERE id = :id")
	public abstract FoodDiaryEntry get(long id);

	/**
	 * Get an observable food diary entry by ID.
	 *
	 * @param id the ID to search for
	 *
	 * @return an observable food diary entry
	 */
	@Query("SELECT * FROM diary_food WHERE id = :id")
	public abstract LiveData<FoodDiaryEntry> getLD(long id);

	/**
	 * Get a food diary entry and it's accompanying {@link com.fitnesstracker.database.entities.Food}
	 * as a {@link Meal} by ID.
	 * <p>
	 * This method will return null if there is no food diary entry with the given ID.
	 *
	 * @param foodDiaryEntryId the ID to search for
	 *
	 * @return a meal with a matching ID or null if there is none
	 */
	@Transaction
	@Nullable
	@Query("SELECT * FROM diary_food WHERE id = :foodDiaryEntryId")
	public abstract Meal getMeal(Long foodDiaryEntryId);

	/**
	 * Get an observable food diary entry and it's accompanying {@link
	 * com.fitnesstracker.database.entities.Food} as a {@link Meal} by ID.
	 * <p>
	 * This method will not return null, but the returned {@link LiveData} may contain null.
	 *
	 * @param foodDiaryEntryId the ID to search for
	 *
	 * @return an observable meal with a matching ID
	 */
	@Transaction
	@Query("SELECT * FROM diary_food WHERE id = :foodDiaryEntryId")
	public abstract LiveData<Meal> getMealLD(Long foodDiaryEntryId);

	/**
	 * Get a list of all food diary entries and their accompanying {@link
	 * com.fitnesstracker.database.entities.Food} objects as {@link Meal} objects.
	 *
	 * @return a list of all meals
	 */
	@Transaction
	@Query("SELECT * FROM diary_food ORDER BY time DESC")
	public abstract List<Meal> getAllMeals();

	/**
	 * Get an observable list of all food diary entries and their accompanying {@link
	 * com.fitnesstracker.database.entities.Food} objects as {@link Meal} objects.
	 *
	 * @return an observable list of all meals
	 */
	@Transaction
	@Query("SELECT * FROM diary_food ORDER BY time DESC")
	public abstract LiveData<List<Meal>> getAllMealsLD();

	/**
	 * Update one or more {@link FoodDiaryEntry} entities using {@link Meal} objects.
	 * <p>
	 * This method may assign a food diary entry a different {@link com.fitnesstracker.database.entities.Food},
	 * but it will <b>not</b> commit to the database any changes made to food objects.
	 *
	 * @param meals the meals whose underlying diary entries should be updated
	 *
	 * @return the number of database rows that were updated
	 */
	public int update(Meal... meals) {
		int updates = 0;
		for (Meal meal : meals) {
			updates += update(meal.getFoodDiaryEntry());
		}
		return updates;
	}
}
