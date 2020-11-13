package com.fitnesstracker.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.fitnesstracker.database.daos.FoodDao;
import com.fitnesstracker.database.daos.FoodDiaryEntryDao;
import com.fitnesstracker.database.entities.Food;
import com.fitnesstracker.database.entities.FoodDiaryEntry;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * A layer of abstraction between the Room database and the user interface.
 */
public class FTViewModel extends AndroidViewModel {

	private final FoodDao foodDao;
	private final FoodDiaryEntryDao foodDiaryEntryDao;

	private final FTDatabase db;
	private final ExecutorService executor;

	private final MutableLiveData<String> foodSearchKey;
	private final LiveData<List<Food>> foods;

//	private final MutableLiveData<Long> foodDiaryEntrySearchKey;
//	private final LiveData<List<FoodDiaryEntry>> foodDiaryEntries;

	private final MutableLiveData<Long> mealSearchKey;
	private final LiveData<List<Meal>> meals;

	public FTViewModel(@NonNull Application application) {
		super(application);

		// Get the database instance
		db = FTDatabase.getDatabase(application);

		// Get DAOs from the database
		foodDao = db.getFoodDao();
		foodDiaryEntryDao = db.getFoodDiaryEntryDao();

		// Get the database's executor
		executor = FTDatabase.getExecutor();

		foodSearchKey = new MutableLiveData<>(null);
		foods = Transformations.switchMap(foodSearchKey, new Function<String, LiveData<List<Food>>>() {
			@Override public LiveData<List<Food>> apply(String name) {
				if (name == null || name.equals("")) {
					return foodDao.getAllLD();
				} else {
					return foodDao.getLD(name);
				}
			}
		});

		mealSearchKey = new MutableLiveData<>(null);
		meals = Transformations.switchMap(mealSearchKey, new Function<Long, LiveData<List<Meal>>>() {
			@Override public LiveData<List<Meal>> apply(Long time) {
				return foodDiaryEntryDao.getAllMealsLD();
			}
		});
	}

	/**
	 * Clear all entries from the database.
	 */
	public void clearAllTables() {
		executor.execute(new Runnable() {
			@Override public void run() {
				db.clearAllTables();
			}
		});
	}

	public LiveData<List<Food>> getFoods() {
		return foods;
	}

	public void setFoodSearchKey(String foodSearchKey) {
		this.foodSearchKey.setValue(foodSearchKey);
	}

	public void insert(final Food... foods) {
		executor.execute(new Runnable() {
			@Override public void run() {
				foodDao.insert(foods);
			}
		});
	}

	public void update(final Food... foods) {
		executor.execute(new Runnable() {
			@Override public void run() {
				foodDao.update(foods);
			}
		});
	}

	public void delete(final Food... foods) {
		executor.execute(new Runnable() {
			@Override public void run() {
				foodDao.delete(foods);
			}
		});
	}

	public void insert(final FoodDiaryEntry... foodDiaryEntries) {
		executor.execute(new Runnable() {
			@Override public void run() {
				foodDiaryEntryDao.insert(foodDiaryEntries);
			}
		});
	}

	public void insert(final Food food, final FoodDiaryEntry foodDiaryEntry) {
		executor.execute(new Runnable() {
			@Override public void run() {
				foodDao.insert(food);
				foodDiaryEntryDao.insert(foodDiaryEntry);
			}
		});
	}

	public LiveData<List<Meal>> getMeals() {
		return meals;
	}

	public void setMealSearchKey(Long mealSearchKey) {
		this.mealSearchKey.setValue(mealSearchKey);
	}


//	public void setFoodDiaryEntrySearchKey(long time) {
//		foodDiaryEntrySearchKey.setValue(time);
//	}
//
//	public LiveData<List<FoodDiaryEntry>> getFoodDiaryEntries() {
//		return foodDiaryEntries;
//	}

}
