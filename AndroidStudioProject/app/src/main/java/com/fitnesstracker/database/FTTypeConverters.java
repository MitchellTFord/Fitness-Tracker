package com.fitnesstracker.database;

import androidx.room.TypeConverter;

/**
 * Class containing type converter methods for {@link FTDatabase}.
 */
public class FTTypeConverters {

	/**
	 * Convert {@link Meal} objects to their {@link FoodDiaryEntry} representations.
	 *
	 * @param meal the meal to convert
	 *
	 * @return the FoodDiaryEntry representation of meal
	 */
	@TypeConverter
	public static FoodDiaryEntry mealToDiaryEntry(Meal meal) {
		return meal.getDiaryEntry();
	}
}
