package com.fitnesstracker.database;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.fitnesstracker.database.entities.Food;
import com.fitnesstracker.database.entities.FoodDiaryEntry;

import java.util.Objects;

/**
 * This class models the meals recorded in the database entity {@link FoodDiaryEntry}.
 * <p>
 * Methods in {@link com.fitnesstracker.database.daos.FoodDiaryEntryDao} allow the programmer to
 * modify meal objects' underlying diary entries. For example, if you modify a Meal object's food
 * field using {@link Meal#setFood(Food)} than insert it into the database using {@link
 * com.fitnesstracker.database.daos.FoodDiaryEntryDao#update(Meal)}.
 * <p>
 * Modifications made to this object's <code>food</code> will <b>not</b> be propagated when using
 * methods such as the one mentioned above.
 *
 * @author Mitchell Ford
 */
public class Meal {

	/**
	 * The {@link FoodDiaryEntry} this meal is modelled off of.
	 */
	@Embedded
	private FoodDiaryEntry foodDiaryEntry;

	/**
	 * The food that was eaten in the meal represented by {@link Meal#foodDiaryEntry}.
	 * <p>
	 * In database queries, this field is automatically filled based on the relation between the
	 * <code>food_id</code> column in {@link FoodDiaryEntry} and the <code>id</code> column in
	 * {@link Food}.
	 */
	@Relation(
			parentColumn = "food_id",
			entityColumn = "id"
	)
	private Food food;

	/**
	 * Constructor that specifies a diary entry and the food it corresponds to.
	 * <p>
	 * This is the constructor that the Room database should use; all others must have the
	 * <code>@Ignore</code> annotation.
	 *
	 * @param foodDiaryEntry the diary entry this meal is modelled off of
	 * @param food           the food that was eaten in this meal
	 */
	public Meal(@NonNull FoodDiaryEntry foodDiaryEntry, @NonNull Food food) {
		setFoodDiaryEntry(foodDiaryEntry);
		setFood(food);
	}

	/**
	 * Get the {@link FoodDiaryEntry} this meal is modelled off of.
	 *
	 * @return the diary entry this meal is modelled off of
	 */
	@NonNull
	public FoodDiaryEntry getFoodDiaryEntry() {
		return foodDiaryEntry;
	}

	/**
	 * Set the {@link FoodDiaryEntry} this meal is modelled off of.
	 * <p>
	 * It is unlikely that there is a practical usage of method outside of this class.
	 *
	 * @param foodDiaryEntry the diary entry to set
	 */
	public void setFoodDiaryEntry(@NonNull FoodDiaryEntry foodDiaryEntry) {
		this.foodDiaryEntry = foodDiaryEntry;
	}

	/**
	 * Get the food that was eaten in this meal.
	 *
	 * @return the food that was eaten in this meal
	 */
	public Food getFood() {
		return food;
	}

	/**
	 * Set the food that was eaten in this meal.
	 * <p>
	 * Calls to this method also modify <code>foodId</code> in {@link Meal#foodDiaryEntry}.
	 *
	 * @param food the new food that was eaten in this meal
	 */
	public void setFood(@NonNull Food food) {
		this.food = food;
		this.foodDiaryEntry.setFoodId(food.getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Meal meal = (Meal) o;
		return Objects.equals(getFoodDiaryEntry(), meal.getFoodDiaryEntry()) &&
				Objects.equals(getFood(), meal.getFood());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getFoodDiaryEntry(), getFood());
	}

	@NonNull
	@Override
	public String toString() {
		return "Meal{" +
				"foodDiaryEntry=" + foodDiaryEntry +
				", food=" + food +
				'}';
	}
}
