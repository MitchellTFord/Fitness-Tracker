package com.fitnesstracker.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.fitnesstracker.ui.DiaryEntryAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * A layer of abstraction between the Room database and the user interface.
 */
public class FTViewModel extends AndroidViewModel {

	private final FTDao dao;
	private final FTDatabase db;
	private final ExecutorService executor;

	private final MutableLiveData<String> foodSearchKey;
	private final LiveData<List<Food>> foods;

	private final MutableLiveData<Long> foodDiaryEntrySearchKey;
	private final LiveData<List<FoodDiaryEntry>> foodDiaryEntries;

	private final MediatorLiveData<List<FoodDiaryEntry>> mealTrigger;
	private final LiveData<List<Meal>> meals;

	public FTViewModel(@NonNull Application application) {
		super(application);
		db = FTDatabase.getDatabase(application);
		dao = db.getDao();
		executor = FTDatabase.getExecutor();

		foodSearchKey = new MutableLiveData<>(null);
		foods = Transformations.switchMap(foodSearchKey, new Function<String, LiveData<List<Food>>>() {
			@Override public LiveData<List<Food>> apply(String name) {
				if (name == null) {
					return dao.getAllFoods();
				} else {
					return dao.getFood(name);
				}
			}
		});

		foodDiaryEntrySearchKey = new MutableLiveData<>(null);
		foodDiaryEntries = Transformations.switchMap(foodDiaryEntrySearchKey, new Function<Long, LiveData<List<FoodDiaryEntry>>>() {
			@Override public LiveData<List<FoodDiaryEntry>> apply(Long time) {
				if (time == null || time == 0) {
					return dao.getAllFoodDiaryEntries();
				} else {
					return dao.getFoodDiaryEntries(time, time + 86_400_000);
				}
			}
		});

		mealTrigger = new MediatorLiveData<>();
		mealTrigger.addSource(foodDiaryEntries, new Observer<List<FoodDiaryEntry>>() {
			@Override public void onChanged(List<FoodDiaryEntry> foodDiaryEntries) {
				mealTrigger.setValue(foodDiaryEntries);
			}
		});
		mealTrigger.addSource(foods, new Observer<List<Food>>() {
			@Override public void onChanged(List<Food> foods) {
				// Refresh on food table update
				mealTrigger.setValue(mealTrigger.getValue());
			}
		});

		meals = Transformations.switchMap(mealTrigger, new Function<List<FoodDiaryEntry>, LiveData<List<Meal>>>() {
			@Override public LiveData<List<Meal>> apply(List<FoodDiaryEntry> foodDiaryEntries) {
				return createMeals(foodDiaryEntries);
			}
		});
	}

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

	public void insert(final FoodDiaryEntry... foodDiaryEntries) {
		executor.execute(new Runnable() {
			@Override public void run() {
				dao.insert(foodDiaryEntries);
			}
		});
	}

	/**
	 * Update one or more meals in the database.
	 *
	 * @param foodDiaryEntries one or more meals objects to update
	 *
	 * @return the number of items updated
	 */
	public int update(final FoodDiaryEntry... foodDiaryEntries) {
		final AtomicReference<Integer> numUpdates = new AtomicReference<>();
		executor.execute(new Runnable() {
			@Override public void run() {
				numUpdates.set(dao.update(foodDiaryEntries));
			}
		});
		return numUpdates.get();
	}

	/**
	 * Delete one or more meals from the database.
	 *
	 * @param foodDiaryEntries one or more meals objects to delete
	 *
	 * @return the number of items deleted
	 */
	public int delete(final FoodDiaryEntry... foodDiaryEntries) {
		final AtomicReference<Integer> numDeletes = new AtomicReference<>();
		executor.execute(new Runnable() {
			@Override public void run() {
				numDeletes.set(dao.delete(foodDiaryEntries));
			}
		});
		return numDeletes.get();
	}

	public void setFoodDiaryEntrySearchKey(long time) {
		foodDiaryEntrySearchKey.setValue(time);
	}

	public LiveData<List<FoodDiaryEntry>> getFoodDiaryEntries() {
		return foodDiaryEntries;
	}

	public void setFoodSearchKey(String foodSearchKey) {
		this.foodSearchKey.setValue(foodSearchKey);
	}

	public LiveData<List<Meal>> getMeals() {
		return meals;
	}

	private MutableLiveData<List<Meal>> createMeals(final List<FoodDiaryEntry> foodDiaryEntries) {
		final List<Meal> meals = new ArrayList<>();
		if(foodDiaryEntries == null) {
			return new MutableLiveData<>(meals);
		}

		executor.execute(new Runnable() {
			@Override public void run() {
				for(FoodDiaryEntry foodDiaryEntry : foodDiaryEntries) {
					meals.add(new Meal(
							foodDiaryEntry.getId(),
							dao.getFood(foodDiaryEntry.getFoodId()),
							foodDiaryEntry.getNumServings(),
							foodDiaryEntry.getTime()));
				}
			}
		});
		return new MutableLiveData<>(meals);
	}

	public void makeSampleMeal() {
		executor.execute(new Runnable() {
			@Override public void run() {
				try {
					Food food = Food.makeRandom();
					dao.insert(food);

					Thread.sleep(50);

					Meal meal = new Meal(food, 1, System.currentTimeMillis());
					dao.insert(meal.getDiaryEntry());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
