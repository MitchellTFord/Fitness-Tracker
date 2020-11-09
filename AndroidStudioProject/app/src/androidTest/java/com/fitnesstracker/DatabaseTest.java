package com.fitnesstracker;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.fitnesstracker.database.DiaryEntry;
import com.fitnesstracker.database.FTDao;
import com.fitnesstracker.database.FTDatabase;
import com.fitnesstracker.database.Food;
import com.fitnesstracker.database.FoodServingTuple;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

/**
 * JUnit test class for ensuring basic functionality of {@link FTDatabase}, {@link FTDao}, and their
 * interactions with their associated entity classes.
 * <p>
 * These tests run on an Android device (or emulator) rather than the developer's computer.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
	private FTDao dao;
	private FTDatabase db;

	@Before
	public void createDB() {
		Context context = ApplicationProvider.getApplicationContext();
		db = Room.inMemoryDatabaseBuilder(context, FTDatabase.class).build();
		dao = db.getDao();
	}

	@After
	public void closeDB() {
		db.close();
	}

	@Test
	public void foodInsertDeleteTest() throws Exception {
		// Assert that there are currently no foods in the database
		assertEquals(0, dao.getAllFoods().getValue().size());

		// Add a new Food object to the database
		Food testFood = new Food("test food", "cup", 2.6);
		dao.insert(testFood);

		// Assert that there is now one food in the database
		assertEquals(1, dao.getAllFoods().getValue().size());

		// Ensure that the food object can be retrieved
		assertEquals(testFood, dao.getFood(testFood.getId()));

		// Ensure that the food object can be deleted
		assertEquals(1, dao.delete(testFood));

		// Assert that there are currently no foods in the database
		assertEquals(0, dao.getAllFoods().getValue().size());
	}

	@Test
	public void foodUpdateTest() throws Exception {
		// Assert that there are currently no foods in the database
		assertEquals(0, dao.getAllFoods().getValue().size());

		// Add a new Food object to the database
		Food testFood = new Food("test food", "cup", 2.6);
		dao.insert(testFood);

		// Attempt to update this food
		testFood.setName("new name for test food");
		dao.update(testFood);

		// Ensure that the food object can still be retrieved by ID
		assertEquals(testFood, dao.getFood(testFood.getId()));

		// Ensure that the food object can be retrieved using its new name
		assertEquals(testFood, dao.getFood(testFood.getName()).getValue().get(0));

		// Remove the food object to clean up for other tests
		dao.delete(testFood);
	}

	/**
	 * Tests that {@link com.fitnesstracker.database.DiaryEntry} objects can be inserted, updated,
	 * and deleted.
	 */
	@Test
	public void basicDiaryEntryTest() {
		// Assert that there are currently no diary entries in the database
		assertEquals(0, dao.getAllDiaryEntries().getValue().size());

		DiaryEntry testDiaryEntry = new DiaryEntry(new Date(System.currentTimeMillis()));
		dao.insert(testDiaryEntry);

		// Assert that there is now one diary entry in the database
		assertEquals(1, dao.getAllDiaryEntries().getValue().size());

		// Attempt to update this diary entry
		testDiaryEntry.setDate(new Date(System.currentTimeMillis() + 1000));
		dao.update(testDiaryEntry);

		// Ensure that the diary entry can still be retrieved by ID
		assertEquals(testDiaryEntry, dao.getDiaryEntry(testDiaryEntry.getId()));

		// Ensure that the diary entry can be deleted
		dao.delete(testDiaryEntry);
		assertEquals(0, dao.getAllDiaryEntries().getValue().size());
	}

	@Test
	public void diaryImplementationTest() {
		// Assert that there are currently no diary entries in the database
		assertEquals(0, dao.getAllDiaryEntries().getValue().size());

		// Create and add a diary entry and a food
		DiaryEntry testDiaryEntry = new DiaryEntry(new Date(System.currentTimeMillis()));
		dao.insert(testDiaryEntry);
		Food testFood = new Food("test food name", "test serving unit", 2.8543);
		dao.insert(testFood);

		// Add a new diary entry cross reference
		double numServings1 = 3.62;
		dao.addDiaryEntry(testDiaryEntry, testFood, numServings1);

		// Assert that there is now one food-diary cross reference
		assertEquals(1, dao.getAllDiaryEntries().getValue().size());

		// Query the database for all FoodServingTuple instances associated with this diary entry
		List<FoodServingTuple> results = dao.getFoodsFromDiary(testDiaryEntry).getValue();
		assertEquals(1, results.size());
		assertEquals(testFood, results.get(0).getFood());
		assertEquals(numServings1, results.get(0).getNumServings(), 0);

		// TODO: Write more tests
	}
}
