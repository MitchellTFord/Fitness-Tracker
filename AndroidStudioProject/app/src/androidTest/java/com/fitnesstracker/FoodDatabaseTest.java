package com.fitnesstracker;

import com.fitnesstracker.database.daos.FoodDao;
import com.fitnesstracker.database.entities.Food;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class FoodDatabaseTest extends DatabaseTest {

	private FoodDao foodDao;

	private static final Comparator<Food> COMP_ID = (o1, o2) -> (int) Math.signum(o1.getId() - o2.getId());

	private static final List<Food> FOODS = Arrays.asList(
			new Food("testName0", "testUnit0", 0d),
			new Food("testName0", "testUnit1", 1d),
			new Food("testName1", "testUnit2", 2d),
			new Food("testName1", "testUnit3", 3d),
			new Food("testName2", "testUnit4", 4d)
	);
	static {
		FOODS.sort(COMP_ID);
	}

	@Before
	public void getDao() {
		foodDao = db.getFoodDao();
	}

	@Test
	public void foodTest_Insert_Retrieve() {
		// Clear DB before starting
		db.clearAllTables();

		// Insert food objects into the database
		foodDao.insert(FOODS);

		// Retrieve all foods from the database
		List<Food> dbFoods = foodDao.getAll();

		// Assert that all foods were properly inserted and retrieved
		Assert.assertEquals(FOODS.size(), dbFoods.size());
		Assert.assertTrue(dbFoods.containsAll(FOODS));
	}

	@Test
	public void foodTest_Query_By_ID() {
		// Clear DB before starting
		db.clearAllTables();

		// Insert food objects into the database
		foodDao.insert(FOODS);

		// Attempt to retrieve each of the food objects by ID
		for(Food expected : FOODS) {
			// Query the database for a food with a matching ID
			Food actual = foodDao.get(expected.getId());

			// Assert that the correct food was found
			Assert.assertEquals(expected, actual);
		}
	}

	@Test
	public void foodTest_Query_By_Name() {
		// Clear DB before starting
		db.clearAllTables();

		// Insert food objects into the database
		foodDao.insert(FOODS);

		Map<String, List<Food>> foodsByName = FOODS.stream().collect(Collectors.toMap(
				// Key mapper
				Food::getName,

				// Value mapper
				food -> {
					List<Food> list = new LinkedList<>();
					list.add(food);
					return list;
				},

				// Merge function
				(foods, newFoods) -> {
					foods.addAll(newFoods);
					return foods;
				}
		));

		for(Map.Entry<String, List<Food>> entry : foodsByName.entrySet()) {
			// Get all foods with this name from the database
			List<Food> dbFoods = foodDao.get(entry.getKey());

			// Assert that all foods with matching names were retrieved
			Assert.assertEquals(entry.getValue().size(), dbFoods.size());
			Assert.assertTrue(dbFoods.containsAll(entry.getValue()));
		}
	}
}
