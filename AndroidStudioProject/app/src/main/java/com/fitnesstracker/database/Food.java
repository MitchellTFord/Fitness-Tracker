package com.fitnesstracker.database;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * Class representing a food item.
 *
 * <p>Consists of a unique ID, a name, a serving size, and all the necessary nutrition facts.</p>
 */
@Entity(tableName = "food",
        primaryKeys = "id")
public class Food {
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
	 *
	 * Creates a {@link NutritionInfo} object with default fields.
	 *
	 * @param name
	 */
	public Food(@NotNull String name, @NotNull String servingUnit, @NotNull Double servingSize) {
		Random rand = new Random();
		this.setId(rand.nextLong());
		this.setName(name);
		this.setServingUnit(servingUnit);
		this.setServingSize(servingSize);

		// Create a default NutritionInfo object
		this.setNutritionInfo(new NutritionInfo());
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
	 *
	 * This method should only be called by the Room database.
	 *
	 * @param id
	 */
	@Deprecated
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(@NotNull String name) {
		this.name = name;
	}

	public double getServingSize() {
		return servingSize;
	}

	public void setServingSize(double servingSize) {
		assert servingSize >= 0 : "Serving size must be greater than or equal to zero";
		this.servingSize = servingSize;
	}

	public String getServingUnit() {
		return servingUnit;
	}

	public void setServingUnit(@NotNull String servingUnit) {
		this.servingUnit = servingUnit;
	}

	public NutritionInfo getNutritionInfo() {
		return nutritionInfo;
	}

	public void setNutritionInfo(@NotNull NutritionInfo nutritionInfo) {
		this.nutritionInfo = nutritionInfo;
	}
}
