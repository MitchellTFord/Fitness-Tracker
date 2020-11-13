package com.fitnesstracker;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.fitnesstracker.database.FoodDiaryEntry;
import com.fitnesstracker.database.Meal;
import com.fitnesstracker.database.daos.FTDao;
import com.fitnesstracker.database.FTDatabase;
import com.fitnesstracker.database.Food;
import com.fitnesstracker.database.daos.FoodDao;
import com.fitnesstracker.database.daos.FoodDiaryEntryDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * JUnit test class for ensuring basic functionality of {@link FTDatabase}, {@link FTDao}, and their
 * interactions with their associated entity classes.
 * <p>
 * These tests run on an Android device (or emulator) rather than the developer's computer.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

	private FTDatabase db;
	private FoodDao foodDao;
	private FoodDiaryEntryDao foodDiaryEntryDao;

	@Before
	public void createDB() {
		Context context = ApplicationProvider.getApplicationContext();
		db = Room.inMemoryDatabaseBuilder(context, FTDatabase.class).build();
		foodDao = db.getFoodDao();
		foodDiaryEntryDao = db.getFoodDiaryEntryDao();
	}

	@After
	public void closeDB() {
		db.close();
	}

	@Test
	public void mealTest() {
		db.clearAllTables();

		Food food = Food.makeRandom();
		foodDao.insert(food);

		FoodDiaryEntry foodDiaryEntry = new FoodDiaryEntry(food, 1, 50000);
		foodDiaryEntryDao.insert(foodDiaryEntry);

		Meal testMeal = new Meal(foodDiaryEntry, food);

		List<Meal> meals = foodDiaryEntryDao.getAllMeals();

		assertEquals(1, meals.size());

		assertEquals(meals.get(0), testMeal);
	}
}
