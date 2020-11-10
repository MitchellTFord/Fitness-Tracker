package com.fitnesstracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

/**
 * A data access object to be used with {@link FTDatabase}.
 *
 * @author Mitchell Ford
 */
@Dao
public abstract class FTDao {
	/**
	 * Query the database for a Food with a given id.
	 *
	 * @param id the id of the Food to search for
	 *
	 * @return the Food with the given id, or null if no such Food exists
	 */
	@NotNull
	@Query("SELECT * FROM Food WHERE Food.id = :id")
	public abstract Food getFood(long id);

	@NotNull
	@Query("SELECT * FROM Food WHERE Food.name LIKE :name")
	public abstract LiveData<List<Food>> getFood(@NotNull String name);

	/**
	 * Query the database for a {@link java.util.List} of all the Foods it has.
	 *
	 * @return a {@link List} of all the Foods in the database
	 */
	@Query("SELECT * FROM Food " +
			       "ORDER BY name")
	public abstract LiveData<List<Food>> getAllFoods();

	/**
	 * Insert {@link Food} objects into the database.
	 *
	 * @param food one or more Food objects to insert into the database
	 */
	@Insert(entity = Food.class)
	public abstract void insert(@NotNull Food... food);

	/**
	 * Update {@link Food} objects in the database.
	 *
	 * @param food one or more Food objects to update
	 *
	 * @return the number of rows that were updated
	 */
	@Update(entity = Food.class)
	public abstract int update(@NotNull Food... food);

	/**
	 * Delete {@link Food} objects from the database.
	 *
	 * @param food one or more Food objects to delete
	 *
	 * @return the number of rows that were deleted
	 */
	@Delete(entity = Food.class)
	public abstract int delete(@NotNull Food... food);

	/**
	 * Insert {@link FoodDiaryEntry} objects into the database.
	 *
	 * @param foodDiaryEntries one or more Food objects to insert into the database
	 */
	@Insert(entity = FoodDiaryEntry.class)
	public abstract void insert(@NotNull FoodDiaryEntry... foodDiaryEntries);

	/**
	 * Update {@link FoodDiaryEntry} objects in the database.
	 *
	 * @param foodDiaryEntries one or more Food objects to update
	 *
	 * @return the number of rows that were updated
	 */
	@Update(entity = FoodDiaryEntry.class)
	public abstract int update(@NotNull FoodDiaryEntry... foodDiaryEntries);

	/**
	 * Delete {@link FoodDiaryEntry} objects from the database.
	 *
	 * @param foodDiaryEntries one or more Food objects to delete
	 *
	 * @return the number of rows that were deleted
	 */
	@Delete(entity = FoodDiaryEntry.class)
	public abstract int delete(@NotNull FoodDiaryEntry... foodDiaryEntries);

	@Query("SELECT * " +
			       "FROM diary_food D " +
			       "ORDER BY D.time DESC")
	public abstract LiveData<List<FoodDiaryEntry>> getAllFoodDiaryEntries();

	@Query("SELECT * " +
			       "FROM diary_food D " +
			       "WHERE D.time >= :startTime AND D.time < :endTime " +
			       "ORDER BY D.time DESC")
	public abstract LiveData<List<FoodDiaryEntry>> getFoodDiaryEntries(long startTime, long endTime);

	@Query("SELECT * " +
			       "FROM diary_food D " +
			       "WHERE D.time >= :startTime AND D.time < :endTime " +
			       "ORDER BY D.time DESC")
	public abstract List<FoodDiaryEntry> getFoodDiaryEntriesRaw(long startTime, long endTime);

	@Query("SELECT D.id AS id, F.*, D.num_servings, D.time " +
			       "FROM diary_food D, food F " +
			       "WHERE D.time >= :startTime AND D.time < :endTime " +
			       "AND D.food_id = F.id " +
			       "ORDER BY D.time DESC")
	public abstract LiveData<List<Meal>> getMeals(long startTime, long endTime);

	@Query("SELECT * " +
			       "FROM diary_food D, food F " +
			       "WHERE D.food_id = F.id " +
			       "ORDER BY D.time DESC")
	public abstract LiveData<List<Meal>> getAllMeals();

	public Meal getMeal(FoodDiaryEntry foodDiaryEntry) {
		return getMeal(foodDiaryEntry);
	}
}
