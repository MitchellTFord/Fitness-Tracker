package com.fitnesstracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.fitnesstracker.database.daos.FoodDao;
import com.fitnesstracker.database.daos.FoodDiaryEntryDao;
import com.fitnesstracker.database.daos.NutritionGoalDao;
import com.fitnesstracker.database.entities.Food;
import com.fitnesstracker.database.entities.FoodDiaryEntry;
import com.fitnesstracker.database.entities.NutritionGoal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Food.class, FoodDiaryEntry.class, NutritionGoal.class},
		version = 1,
		exportSchema = false)
@TypeConverters(FTTypeConverters.class)
public abstract class FTDatabase extends RoomDatabase {
	//public abstract FTDao getDao();

	public abstract FoodDao getFoodDao();

	public abstract FoodDiaryEntryDao getFoodDiaryEntryDao();

	public abstract NutritionGoalDao getNutritionGoalDao();

	private static volatile FTDatabase INSTANCE;
	private static final int NUMBER_OF_THREADS = 4;
	private static final ExecutorService executor =
			Executors.newFixedThreadPool(NUMBER_OF_THREADS);

	public static FTDatabase getDatabase(final Context context) {
		if (INSTANCE == null) {
			synchronized (FTDatabase.class) {
				INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FTDatabase.class,
						"ft_database").build();
			}
		}
		return INSTANCE;
	}

	public static ExecutorService getExecutor() {
		return executor;
	}


}
