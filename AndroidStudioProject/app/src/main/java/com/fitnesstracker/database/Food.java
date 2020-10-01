package com.fitnesstracker.database;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Class representing a food item.
 *
 * <p>Consists of a unique ID, a name, a serving size, and all the necessary nutrition facts.</p>
 */
@Entity(tableName = "Food")
public class Food {
	/** A randomly assigned identifier for this Food. */
	@PrimaryKey
	@ColumnInfo(name = "id")
	public long id;

	/** The name of this Food. */
	@ColumnInfo(name = "name")
	public String name;

	/** The numeric part of a serving listing. */
	@ColumnInfo(name = "serving_size")
	public double servingSize;

	/** The non-numeric part of a serving listing. */
	@ColumnInfo(name = "serving_unit")
	public String servingUnit;

	/** The nutrition facts of one serving of this food. */
	@Embedded
	public NutritionInfo nutritionInfo;

}
