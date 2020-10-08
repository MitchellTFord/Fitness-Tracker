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
	public abstract LiveData<Food> getFood(long id);

	@NotNull
	@Query("SELECT * FROM Food WHERE Food.name LIKE :name")
	public abstract LiveData<List<Food>> getFood(@NotNull String name);

	/**
	 * Query the database for a {@link java.util.List} of all the Foods it has.
	 *
	 * @return a {@link List} of all the Foods in the database
	 */
	@Query("SELECT * FROM Food")
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
	 * Insert {@link DiaryEntry} objects into the database.
	 *
	 * @param diaryEntry one or more DiaryEntry objects to insert
	 */
	@Insert(entity = DiaryEntry.class)
	public abstract void insert(@NotNull DiaryEntry... diaryEntry);

	/**
	 * Update one ore more {@link DiaryEntry} objects in the database.
	 *
	 * @param diaryEntry one or more DiaryEntry objects to update
	 *
	 * @return the number of rows that were updated
	 */
	@Update(entity = DiaryEntry.class)
	public abstract int update(@NotNull DiaryEntry... diaryEntry);

	/**
	 * Delete one ore more {@link DiaryEntry} objects from the database.
	 *
	 * @param diaryEntry one or more DiaryEntry objects to delete
	 *
	 * @return the number of rows that were deleted
	 */
	@Delete(entity = DiaryEntry.class)
	public abstract int delete(@NotNull DiaryEntry... diaryEntry);

	@Nullable
	@Query("SELECT * FROM diary_entry WHERE diary_entry.id = :id")
	public abstract LiveData<DiaryEntry> getDiaryEntry(long id);

	@NotNull
	@Query("SELECT * FROM diary_entry WHERE diary_entry.date = :date")
	public abstract LiveData<List<DiaryEntry>> getDiaryEntries(Date date);

	/**
	 * Query the database for a {@link List} of all the diary entries it has.
	 *
	 * @return a list of all the diary entries in the database
	 */
	@NotNull
	@Query("SELECT * FROM diary_entry")
	public abstract LiveData<List<DiaryEntry>> getAllDiaryEntries();

	/**
	 * Get a list of foods and numbers of servings associated with a diary entry by its ID.
	 *
	 * @param diaryEntryID the id of the diary entry to get the foods of
	 *
	 * @return a list of {@link FoodServingTuple.FoodServingTuple} objects associated with this diary entry
	 */
	@NotNull
	@Transaction
	@Query("SELECT food.*, diary_food.num_servings as numServings " +
			       "FROM food, diary_entry, diary_food " +
			       "WHERE food.id = diary_food.food_id " +
			       "AND diary_entry.id = diary_food.diary_entry_id " +
			       "AND diary_entry.id = :diaryEntryID")
	public abstract LiveData<List<FoodServingTuple>> getFoodsFromDiary(long diaryEntryID);

	/**
	 * Get a list of foods and numbers of servings associated with a diary entry.
	 *
	 * @param diaryEntry the diary entry to get the foods of
	 *
	 * @return a list of {@link FoodServingTuple} objects associated with this diary entry
	 */
	@NotNull
	public LiveData<List<FoodServingTuple>> getFoodsFromDiary(@NotNull DiaryEntry diaryEntry) {
		return getFoodsFromDiary(diaryEntry.getId());
	}

	@Insert(entity = DiaryEntryFoodCrossRef.class)
	public abstract void insert(@NotNull DiaryEntryFoodCrossRef... diaryEntryFoodCrossRef);

	@Update(entity = DiaryEntryFoodCrossRef.class)
	public abstract int update(@NotNull DiaryEntryFoodCrossRef... diaryEntryFoodCrossRef);

	@Delete(entity = DiaryEntryFoodCrossRef.class)
	public abstract int delete(@NotNull DiaryEntryFoodCrossRef... diaryEntryFoodCrossRef);

	@Query("SELECT * FROM diary_food")
	public abstract LiveData<List<DiaryEntryFoodCrossRef>> getAllDiaryEntryFoodCrossRef();

	public void addDiaryEntry(DiaryEntry diaryEntry, Food food, double numServings) {
		insert(new DiaryEntryFoodCrossRef(diaryEntry, food, numServings));
	}
}
