package com.fitnesstracker.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.fitnesstracker.database.entities.FoodDiaryEntry;
import com.fitnesstracker.database.Meal;

import java.util.Calendar;
import java.util.List;

/**
 * A data access object for {@link FoodDiaryEntry} entities to be used with {@link
 * com.fitnesstracker.database.FTDatabase}.
 * <p>
 * This class also contains methods for getting {@link Meal} objects from the database.
 */
@Dao
public abstract class FoodDiaryEntryDao extends FTDao<FoodDiaryEntry> {



	@Query("SELECT * FROM diary_food ORDER BY time DESC")
	public abstract List<FoodDiaryEntry> getAll();

	@Query("SELECT * FROM diary_food ORDER BY time DESC")
	public abstract LiveData<List<FoodDiaryEntry>> getAllLD();

	@Query("SELECT * FROM diary_food WHERE id = :id")
	public abstract FoodDiaryEntry get(long id);

	@Query("SELECT * FROM diary_food WHERE id = :id")
	public abstract LiveData<FoodDiaryEntry> getLD(long id);

	@Transaction
	@Query("SELECT * FROM diary_food WHERE id = :foodDiaryEntryId")
	public abstract Meal getMeal(Long foodDiaryEntryId);

	@Transaction
	@Query("SELECT * FROM diary_food WHERE id = :foodDiaryEntryId")
	public abstract LiveData<Meal> getMealLD(Long foodDiaryEntryId);

	@Transaction
	@Query("SELECT * FROM diary_food ORDER BY time DESC")
	public abstract List<Meal> getAllMeals();

	@Transaction
	@Query("SELECT * FROM diary_food ORDER BY time DESC")
	public abstract LiveData<List<Meal>> getAllMealsLD();

	@Transaction
	@Query("SELECT * " +
			       "FROM diary_food " +
			       "WHERE time BETWEEN :startTime AND :endTime " +
			       "ORDER BY time DESC")
	public abstract List<Meal> getMeals(long startTime, long endTime);

	@Transaction
	@Query("SELECT * " +
			       "FROM diary_food " +
			       "WHERE time BETWEEN :startTime AND :endTime " +
			       "ORDER BY time DESC")
	public abstract LiveData<List<Meal>> getMealsLD(long startTime, long endTime);

//	public List<Meal> getMeals(Calendar calendar) {
//		return getMeals(calendar.getTimeInMillis(), calendar.getTimeInMillis() + SECONDS_PER_DAY);
//	}
//
//	public LiveData<List<Meal>> getMealsLD(Calendar calendar) {
//		return getMealsLD(calendar.getTimeInMillis(), calendar.getTimeInMillis() + SECONDS_PER_DAY);
//	}
}
