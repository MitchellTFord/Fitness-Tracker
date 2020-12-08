package com.fitnesstracker;

import com.fitnesstracker.database.Meal;
import com.fitnesstracker.database.daos.FoodDao;
import com.fitnesstracker.database.daos.FoodDiaryEntryDao;
import com.fitnesstracker.database.entities.Food;
import com.fitnesstracker.database.entities.FoodDiaryEntry;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MealDatabaseTest extends DatabaseTest {

	private FoodDao foodDao;
	private FoodDiaryEntryDao mealDao;

	private static final Comparator<Food> FOOD_COMP_ID = (o1, o2) -> (int) Math.signum(o1.getId() - o2.getId());

	private static final List<Food> FOODS = Arrays.asList(
			new Food("testName0", "testUnit0", 0d, Food.NutritionInfo.makeRandom(0)),
			new Food("testName0", "testUnit1", 1d, Food.NutritionInfo.makeRandom(1)),
			new Food("testName1", "testUnit2", 2d, Food.NutritionInfo.makeRandom(2)),
			new Food("testName1", "testUnit3", 3d, Food.NutritionInfo.makeRandom(3)),
			new Food("testName2", "testUnit4", 4d, Food.NutritionInfo.makeRandom(4))
	);
	static {
		FOODS.sort(FOOD_COMP_ID);
	}

	private static final List<FoodDiaryEntry> FOOD_DIARY_ENTRIES = Arrays.asList(
			FoodDiaryEntry.makeRandom(0, FOODS),
			FoodDiaryEntry.makeRandom(1, FOODS),
			FoodDiaryEntry.makeRandom(2, FOODS),
			FoodDiaryEntry.makeRandom(3, FOODS),
			FoodDiaryEntry.makeRandom(4, FOODS)
	);

	@Before
	public void setup() {
		foodDao = db.getFoodDao();
		mealDao = db.getFoodDiaryEntryDao();
	}

	@Test
	public void mealTest_Insert_Retrieve() {
		db.clearAllTables();

		foodDao.insert(FOODS);
		mealDao.insert(FOOD_DIARY_ENTRIES);

		List<FoodDiaryEntry> dbMeals = mealDao.getAll();

		assertEquals(FOOD_DIARY_ENTRIES.size(), dbMeals.size());
		assertTrue(dbMeals.containsAll(FOOD_DIARY_ENTRIES));
	}

	@Test
	public void mealTest_Insert_RetrieveAsMeals() {
		db.clearAllTables();

		foodDao.insert(FOODS);
		mealDao.insert(FOOD_DIARY_ENTRIES);

		List<FoodDiaryEntry> dbDiaryEntries = mealDao.getAll();

		assertEquals(FOOD_DIARY_ENTRIES.size(), dbDiaryEntries.size());
		assertTrue(dbDiaryEntries.containsAll(FOOD_DIARY_ENTRIES));
	}
}
