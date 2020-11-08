package com.fitnesstracker.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A layer of abstraction between the Room database and the user interface.
 */
public class FTViewModel extends AndroidViewModel {

	private final FTDao dao;
	private final FTDatabase db;
	private final ExecutorService executor;

	private LiveData<List<Food>> food;

	private final MutableLiveData<Date> diaryEntryDateSearchKey;
	private final LiveData<List<DiaryEntry>> diaryEntries;

	private LiveData<List<DiaryEntryFoodCrossRef>> diaryEntryFoodCrossRefs;

	private MutableLiveData<DiaryEntry> mealSearchKey;
	private LiveData<List<FoodServingTuple>> meals;

	public FTViewModel(@NonNull Application application) {
		super(application);
		db = FTDatabase.getDatabase(application);
		dao = db.ftDao();
		executor = FTDatabase.executor;

		diaryEntryDateSearchKey = new MutableLiveData<>(new Date(System.nanoTime()));
		diaryEntries = Transformations.switchMap(
				diaryEntryDateSearchKey,
				new Function<Date, LiveData<List<DiaryEntry>>>() {
					@Override public LiveData<List<DiaryEntry>> apply(final Date date) {
						final AtomicReference<LiveData<List<DiaryEntry>>> result = new AtomicReference<>();

						executor.execute(new Runnable() {
							@Override public void run() {
								if(date == null) {
									result.set(dao.getAllDiaryEntries());
								} else {
									result.set(dao.getDiaryEntries(date));
								}
							}
						});

						return result.get();
					}
				}
		);

		mealSearchKey = new MutableLiveData<>();
		meals = Transformations.switchMap(
				mealSearchKey,
				new Function<DiaryEntry, LiveData<List<FoodServingTuple>>>() {
					@Override public LiveData<List<FoodServingTuple>> apply(final DiaryEntry diaryEntry) {
						final AtomicReference<LiveData<List<FoodServingTuple>>> result = new AtomicReference<>();

						executor.execute(new Runnable() {
							@Override public void run() {
								if(diaryEntry == null) {
									result.set(dao.getFoodsFromAllDiaries());
								} else {
									result.set(dao.getFoodsFromDiary(diaryEntry));
								}
							}
						});

						return result.get();
					}
				}
		);
	}

	public void clearAllTables() {
		executor.execute(new Runnable() {
			@Override public void run() {
				db.clearAllTables();
			}
		});
	}

	public LiveData<List<Food>> getAllFoods() {
		return dao.getAllFoods();
	}

	public LiveData<Food> getFood(final long id) {
		final AtomicReference<LiveData<Food>> food = new AtomicReference<>();
		executor.execute(new Runnable() {
			@Override public void run() {
				food.set(dao.getFood(id));
			}
		});
		return food.get();
	}

	public LiveData<List<Food>> getFoods(final String name) {
		final AtomicReference<LiveData<List<Food>>> foods = new AtomicReference<>();
		executor.execute(new Runnable() {
			@Override public void run() {
				foods.set(dao.getFood(name));
			}
		});
		return foods.get();
	}

	public void insert(final Food... foods) {
		executor.execute(new Runnable() {
			@Override public void run() {
				dao.insert(foods);
			}
		});
	}

	/**
	 * Update one or more foods in the database.
	 *
	 * @param foods one or more food objects to update
	 *
	 * @return the number of items updated
	 */
	public int update(final Food... foods) {
		final AtomicReference<Integer> numUpdates = new AtomicReference<>();
		executor.execute(new Runnable() {
			@Override public void run() {
				numUpdates.set(dao.update(foods));
			}
		});
		return numUpdates.get();
	}

	/**
	 * Delete one or more foods from the database.
	 *
	 * @param foods one or more food objects to delete
	 *
	 * @return the number of items deleted
	 */
	public int delete(final Food... foods) {
		final AtomicReference<Integer> numDeletes = new AtomicReference<>();
		executor.execute(new Runnable() {
			@Override public void run() {
				numDeletes.set(dao.delete(foods));
			}
		});
		return numDeletes.get();
	}

	/**
	 * Get all diary entries from the database.
	 *
	 * @return all diary entries from the database
	 */
	public LiveData<List<DiaryEntry>> getAllDiaryEntries() {
		return getDiaryEntries(null);
	}

//	/**
//	 * Get the diary entry with the given id.
//	 *
//	 * @param id the id of the diary entry to get
//	 *
//	 * @return the diary entry with the matching id
//	 */
//	public LiveData<DiaryEntry> getDiaryEntry(final long id) {
//		final AtomicReference<LiveData<DiaryEntry>> diaryEntry = new AtomicReference<>();
//		executor.execute(new Runnable() {
//			@Override public void run() {
//				diaryEntry.set(dao.getDiaryEntry(id));
//			}
//		});
//		return diaryEntry.get();
//	}

	/**
	 * Get all diary entries matching the current search key {@link FTViewModel#diaryEntryDateSearchKey}.
	 * <p>
	 * The search key is altered using {@link FTViewModel#setDiaryEntryDateSearchKey(Date)}.
	 *
	 * @return a list of diary entries matching the current search key
	 */
	public LiveData<List<DiaryEntry>> getDiaryEntries() {
		return diaryEntries;
	}

	/**
	 * Get all diary entries that occur on a given date.
	 * <p>
	 * If null is passed in, get all diary entries.
	 *
	 * @param date the date to search for
	 *
	 * @return a list of diary entries occurring on the given date
	 */
	public LiveData<List<DiaryEntry>> getDiaryEntries(final Date date) {
		setDiaryEntryDateSearchKey(date);
		return getDiaryEntries();
	}

	public void setDiaryEntryDateSearchKey(Date searchKey) {
		diaryEntryDateSearchKey.setValue(searchKey);
	}

	public int update(final DiaryEntry... diaryEntries) {
		final AtomicReference<Integer> numUpdates = new AtomicReference<>();
		executor.execute(new Runnable() {
			@Override public void run() {
				numUpdates.set(dao.delete(diaryEntries));
			}
		});
		return numUpdates.get();
	}

	public int delete(final DiaryEntry... diaryEntry) {
		final AtomicReference<Integer> numDeletes = new AtomicReference<>();
		executor.execute(new Runnable() {
			@Override public void run() {
				numDeletes.set(dao.delete(diaryEntry));
			}
		});
		return numDeletes.get();
	}

	public void insert(final DiaryEntry diaryEntry) {
		executor.execute(new Runnable() {
			@Override public void run() {
				dao.insert(diaryEntry);
			}
		});
	}

	public void insert(DiaryEntry diaryEntry, Food food, double numServings) {
		insert(new DiaryEntryFoodCrossRef(diaryEntry, food, numServings));
	}

	public void insert(final DiaryEntryFoodCrossRef diaryEntryFoodCrossRef) {
		executor.execute(new Runnable() {
			@Override public void run() {
				dao.insert(diaryEntryFoodCrossRef);
			}
		});
	}

	public int update(final DiaryEntryFoodCrossRef diaryEntryFoodCrossRef) {
		final AtomicReference<Integer> numUpdates = new AtomicReference<>();
		executor.execute(new Runnable() {
			@Override public void run() {
				numUpdates.set(dao.update(diaryEntryFoodCrossRef));
			}
		});
		return numUpdates.get();
	}

	public LiveData<List<FoodServingTuple>> getMealsFromDiary() {
		return meals;
	}

	public LiveData<List<FoodServingTuple>> getMealsFromDiary(final DiaryEntry diaryEntry) {
		setMealSearchKey(diaryEntry);
		return getMealsFromDiary();
	}

	public void setMealSearchKey(DiaryEntry mealSearchKey) {
		this.mealSearchKey.setValue(mealSearchKey);
	}
}
