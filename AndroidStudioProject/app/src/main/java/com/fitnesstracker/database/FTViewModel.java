package com.fitnesstracker.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A layer of abstraction between the Room database and the user interface.
 */
public class FTViewModel extends AndroidViewModel {

	private FTDao dao;
	private ExecutorService executor;

	public FTViewModel(@NonNull Application application) {
		super(application);
		dao = FTDatabase.getDatabase(application).ftDao();
		executor = FTDatabase.databaseWriteExecutor;
	}

	public LiveData<List<Food>> getAllFoods() {
		return dao.getAllFoods();
	}

	public LiveData<Food> getFood(long id) {
		AtomicReference<LiveData<Food>> food = new AtomicReference<>();
		executor.execute(() -> food.set(dao.getFood(id)));
		return food.get();
	}

	public LiveData<List<Food>> getFoods(String name) {
		AtomicReference<LiveData<List<Food>>> foods = new AtomicReference<>();
		executor.execute(() -> foods.set(dao.getFood(name)));
		return foods.get();
	}

	public void insert(Food... foods) {
		executor.execute(() -> dao.insert(foods));
	}

	/**
	 * Update one or more foods in the database.
	 *
	 * @param foods one or more food objects to update
	 *
	 * @return the number of items updated
	 */
	public int update(Food... foods) {
		AtomicReference<Integer> numUpdates = new AtomicReference<>();
		executor.execute(() -> numUpdates.set(dao.update(foods)));
		return numUpdates.get();
	}

	/**
	 * Delete one or more foods from the database.
	 *
	 * @param foods one or more food objects to delete
	 *
	 * @return the number of items deleted
	 */
	public int delete(Food... foods) {
		AtomicReference<Integer> numDeletes = new AtomicReference<>();
		executor.execute(() -> numDeletes.set(dao.delete(foods)));
		return numDeletes.get();
	}

	/**
	 * Get all diary entries from the database.
	 *
	 * @return all diary entries from the database
	 */
	public LiveData<List<DiaryEntry>> getAllDiaryEntries() {
		AtomicReference<LiveData<List<DiaryEntry>>> diaryEntries = new AtomicReference<>();
		executor.execute(() -> diaryEntries.set(dao.getAllDiaryEntries()));
		return diaryEntries.get();
	}

	/**
	 * Get the diary entry with the given id.
	 *
	 * @param id the id of the diary entry to get
	 *
	 * @return the diary entry with the matching id
	 */
	public LiveData<DiaryEntry> getDiaryEntry(long id) {
		AtomicReference<LiveData<DiaryEntry>> diaryEntry = new AtomicReference<>();
		executor.execute(() -> diaryEntry.set(dao.getDiaryEntry(id)));
		return diaryEntry.get();
	}

	/**
	 * Get all diary entries that occur on a given data.
	 *
	 * @param date the date to search for
	 * @return a list of diary entries occurring on the given date
	 */
	public LiveData<List<DiaryEntry>> getDiaryEntries(Date date) {
		AtomicReference<LiveData<List<DiaryEntry>>> diaryEntry = new AtomicReference<>();
		executor.execute(() -> diaryEntry.set(dao.getDiaryEntries(date)));
		return diaryEntry.get();
	}

	public int update(DiaryEntry... diaryEntries) {
		AtomicReference<Integer> numUpdates = new AtomicReference<>();
		executor.execute(() -> numUpdates.set(dao.delete(diaryEntries)));
		return numUpdates.get();
	}

	public int delete(DiaryEntry... diaryEntry) {
		AtomicReference<Integer> numDeletes = new AtomicReference<>();
		executor.execute(() -> numDeletes.set(dao.delete(diaryEntry)));
		return numDeletes.get();
	}

	public void insert(DiaryEntry diaryEntry, Food food, double numServings) {
		insert(new DiaryEntryFoodCrossRef(diaryEntry, food, numServings));
	}

	public void insert(DiaryEntryFoodCrossRef diaryEntryFoodCrossRef) {
		executor.execute(() -> dao.insert(diaryEntryFoodCrossRef));
	}

	public int update(DiaryEntryFoodCrossRef diaryEntryFoodCrossRef) {
		AtomicReference<Integer> numUpdates = new AtomicReference<>();
		executor.execute(() -> numUpdates.set(dao.update(diaryEntryFoodCrossRef)));
		return numUpdates.get();
	}

	public LiveData<List<FoodServingTuple>> getFoodsFromDiary(DiaryEntry diaryEntry) {
		AtomicReference<LiveData<List<FoodServingTuple>>> tuples = new AtomicReference<>();
		executor.execute(() -> tuples.set(dao.getFoodsFromDiary(diaryEntry)));
		return tuples.get();
	}
}
