package com.fitnesstracker.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * A class representing a diary entry describing a meal.
 * <p>
 * When the database is queried, {@link com.fitnesstracker.database.Meal} objects are sometimes
 * returned instead of objects of this class. {@link com.fitnesstracker.database.Meal} objects
 * contain all of the same information as objects of this class with the addition of having the
 * {@link Food} embedded for ease of use.
 * <p>
 * In the documentation of this class, the word "meal" is sometimes used to describe what this class
 * represents. The distinction between this class and {@link com.fitnesstracker.database.Meal} is
 * that this class is <b>a record of a meal</b> whereas <b>the Meal class represents the meal
 * itself</b>.
 */
@Entity(tableName = "diary_food",
        primaryKeys = {"id"},
        foreignKeys = {
		        @ForeignKey(entity = Food.class,
		                    parentColumns = "id",
		                    childColumns = "food_id",
		                    onDelete = ForeignKey.CASCADE)},
        indices = @Index(value = "food_id"))
public class FoodDiaryEntry {

	/**
	 * The unique, randomly assigned ID of the entity.
	 * <p>
	 * This is the primary key of this entity.
	 */
	@ColumnInfo(name = "id")
	private long id;

	/**
	 * The ID of the food that was eaten in this meal.
	 * <p>
	 * This value must correspond to the <code>id</code> of a {@link Food} entity in the database.
	 */
	@ColumnInfo(name = "food_id")
	private long foodId;

	/**
	 * The number of servings of food eaten in this meal.
	 */
	@ColumnInfo(name = "num_servings")
	private double numServings;

	/**
	 * The time that this meal took place.
	 * <p>
	 * This value is stored as the number of milliseconds since January 1st 1970.
	 */
	@ColumnInfo(name = "time")
	private long time;

	/**
	 * Constructor that specifies <code>id</code>, <code>foodId</code>, <code>numServings</code>,
	 * and <code>time</code>.
	 * <p>
	 * This is the constructor that the Room database should use; all others must have the
	 * <code>@Ignore</code> annotation.
	 *
	 * @param id          the id of this diary entry
	 * @param foodId      the id of the food that was eaten
	 * @param numServings the number of servings of food that was eaten
	 * @param time        the time this meal took place
	 */
	public FoodDiaryEntry(long id, long foodId, double numServings, long time) {
		setId(id);
		setFoodId(foodId);
		setNumServings(numServings);
		setTime(time);
	}

	/**
	 * @param foodId      the id of the food that was eaten
	 * @param numServings the number of servings of food that was eaten
	 * @param time        the time this meal took place
	 */
	@Ignore
	public FoodDiaryEntry(long foodId, double numServings, long time) {
		this(generateId(), foodId, numServings, time);
	}

	@Ignore
	public FoodDiaryEntry(Food food, double numServings, long time) {
		this(generateId(), food.getId(), numServings, time);
	}

	public static FoodDiaryEntry makeRandom(List<Food> foods) {
		return makeRandom(0, foods);
	}

	public static FoodDiaryEntry makeRandom(long seed, List<Food> foods) {
		Random rand = new Random(seed);

		return new FoodDiaryEntry(
			rand.nextLong(),
			foods.get(rand.nextInt(foods.size())).getId(),
			rand.nextInt(100),
			Math.abs(rand.nextLong())
		);
	}

	/**
	 * Generate a random ID using {@link Random}.
	 *
	 * @return a random ID
	 */
	public static long generateId() {
		return new Random().nextLong();
	}

	/**
	 * Get the ID of this entity.
	 *
	 * @return the ID of this entity
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set the ID of this entity.
	 * <p>
	 * This method should only be called from a constructor.
	 *
	 * @param id the ID to set
	 */
	private void setId(long id) {
		this.id = id;
	}

	/**
	 * Get the ID of this food.
	 *
	 * @return the ID of this food
	 */
	public long getFoodId() {
		return foodId;
	}

	/**
	 * Set the ID of the food that was eaten in the meal.
	 *
	 * @param foodId the food ID to set
	 */
	public void setFoodId(long foodId) {
		this.foodId = foodId;
	}

	/**
	 * Get the number of servings that were eaten in this meal.
	 *
	 * @return the number of servings that were eaten in this meal
	 */
	public double getNumServings() {
		return numServings;
	}

	/**
	 * Set the number of servings that were eaten in this meal.
	 *
	 * @param numServings the number of servings that were eaten in this meal
	 */
	public void setNumServings(double numServings) {
		this.numServings = numServings;
	}

	/**
	 * Get the time that this meal took place.
	 *
	 * @return the time that this meal took place
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Set the time that this meal took place
	 *
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	@NonNull
	@Override
	public String toString() {
		return "FoodDiaryEntry{" +
				"id=" + id +
				", foodId=" + foodId +
				", numServings=" + numServings +
				", time=" + time +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FoodDiaryEntry that = (FoodDiaryEntry) o;
		return getId() == that.getId() &&
				getFoodId() == that.getFoodId() &&
				Double.compare(that.getNumServings(), getNumServings()) == 0 &&
				getTime() == that.getTime();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getFoodId(), getNumServings(), getTime());
	}
}
