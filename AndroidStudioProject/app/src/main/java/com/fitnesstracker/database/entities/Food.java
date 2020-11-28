package com.fitnesstracker.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import java.util.Random;

/**
 * Class representing a food item.
 * <p>
 * Consists of a unique ID, a name, a serving size, and all the necessary nutrition facts.
 */
@Entity(tableName = "food",
        primaryKeys = "id")
public class Food {

	private static final String[] SAMPLE_NAMES = new String[]{"Apple", "Orange", "Banana", "Egg",
			"Potato", "Bread", "Cheese"};

	private static final String[] SAMPLE_SERVING_UNITS = new String[]{"grams", "cups", "oz", "mL",
			"lbs"};

	/** A randomly assigned identifier for this Food. */
	@ColumnInfo(name = "id")
	private long id;

	/** The name of this Food. */
	@ColumnInfo(name = "name")
	private String name;

	/** The numeric part of a serving listing. */
	@ColumnInfo(name = "serving_size")
	private double servingSize;

	/** The non-numeric part of a serving listing. */
	@ColumnInfo(name = "serving_unit")
	private String servingUnit;

	/** The nutrition facts of one serving of this food. */
	@Embedded(prefix = "nutrition_")
	private NutritionInfo nutritionInfo;

	/**
	 * Constructor that specifies a name and assigns a random ID.
	 * <p>
	 * Creates a {@link NutritionInfo} object with default fields.
	 *
	 * @param name        the name of this food
	 * @param servingUnit the serving unit for this food
	 * @param servingSize the serving size for this food
	 */
	public Food(@NonNull String name, @NonNull String servingUnit, @NonNull Double servingSize) {
		this.setId(new Random().nextLong());
		this.setName(name);
		this.setServingUnit(servingUnit);
		this.setServingSize(servingSize);

		// Create a default NutritionInfo object
		this.setNutritionInfo(new NutritionInfo());
	}

	/**
	 * Create a new Food object with a name randomly selected from {@link Food#SAMPLE_NAMES}, a
	 * serving unit randomly selected from {@link Food#SAMPLE_SERVING_UNITS}, a randomly generated
	 * serving size, and a {@link Food#nutritionInfo} generated using {@link
	 * NutritionInfo#makeRandom()}.
	 *
	 * @return the newly created Food object
	 */
	public static Food makeRandom() {
		Random random = new Random();
		Food food = new Food(
				SAMPLE_NAMES[random.nextInt(SAMPLE_NAMES.length)],
				SAMPLE_SERVING_UNITS[random.nextInt(SAMPLE_SERVING_UNITS.length)],
				random.nextInt(1000)*random.nextDouble()
		);
		food.setNutritionInfo(NutritionInfo.makeRandom());
		return food;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Food food = (Food) o;
		return getId() == food.getId() &&
				Double.compare(food.getServingSize(), getServingSize()) == 0 &&
				getName().equals(food.getName()) &&
				getServingUnit().equals(food.getServingUnit()) &&
				getNutritionInfo().equals(food.getNutritionInfo());
	}

	public long getId() {
		return id;
	}

	/**
	 * Set the id of this Food object.
	 * <p>
	 * This method should only be called by the Room database.
	 *
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Get the name of this food.
	 *
	 * @return the name of this food
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of this food.
	 *
	 * @param name the name to set
	 */
	public void setName(@NonNull String name) {
		this.name = name;
	}

	/**
	 * Get the serving size of this food.
	 *
	 * @return the serving size of this food
	 */
	public double getServingSize() {
		return servingSize;
	}

	/**
	 * Set the serving size of this food.
	 * <p>
	 * It must be non-null and have a value greater than or equal to zero.
	 *
	 * @param servingSize the serving size to set
	 */
	public void setServingSize(@NonNull Double servingSize) {
		if (servingSize < 0) {
			throw new IllegalArgumentException("Serving size must be greater than or equal to zero");
		}
		this.servingSize = servingSize;
	}

	/**
	 * Get the serving unit of this food.
	 *
	 * @return the serving unit of this food
	 */
	public String getServingUnit() {
		return servingUnit;
	}

	/**
	 * Set the serving unit of this food.
	 *
	 * @param servingUnit the serving unit to set
	 */
	public void setServingUnit(@NonNull String servingUnit) {
		this.servingUnit = servingUnit;
	}

	/**
	 * Get the nutrition info of this food.
	 *
	 * @return the nutrition info of this food
	 */
	public NutritionInfo getNutritionInfo() {
		return nutritionInfo;
	}

	/**
	 * Set the nutrition info of this food.
	 *
	 * @param nutritionInfo the nutrition info to set
	 */
	public void setNutritionInfo(@NonNull NutritionInfo nutritionInfo) {
		this.nutritionInfo = nutritionInfo;
	}

	@NonNull
	@Override
	public String toString() {
		return "Food{" +
				"id=" + id +
				", name='" + name + '\'' +
				", servingSize=" + servingSize +
				", servingUnit='" + servingUnit + '\'' +
				", nutritionInfo=" + nutritionInfo +
				'}';
	}

}
