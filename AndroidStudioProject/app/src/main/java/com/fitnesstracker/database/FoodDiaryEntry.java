package com.fitnesstracker.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import java.util.Objects;
import java.util.Random;

@Entity(tableName = "diary_food",
        primaryKeys = {"id"},
        foreignKeys = {
		        @ForeignKey(entity = Food.class,
		                    parentColumns = "id",
		                    childColumns = "food_id",
		                    onDelete = ForeignKey.CASCADE)},
        indices = @Index(value = "food_id"))
public class FoodDiaryEntry {

	@ColumnInfo(name = "id")
	private long id;

	@ColumnInfo(name = "food_id")
	private long foodId;

	@ColumnInfo(name = "num_servings")
	private double numServings;

	@ColumnInfo(name = "time")
	private long time;

	public FoodDiaryEntry(long id, long foodId, double numServings, long time)  {
		setId(id);
		setFoodId(foodId);
		setNumServings(numServings);
		setTime(time);
	}

	@Ignore
	public FoodDiaryEntry(long foodId, double numServings, long time) {
		this(generateId(), foodId, numServings, time);
	}

	public static long generateId() {
		return new Random().nextLong();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getFoodId() {
		return foodId;
	}

	public void setFoodId(long foodId) {
		this.foodId = foodId;
	}

	public double getNumServings() {
		return numServings;
	}

	public void setNumServings(double numServings) {
		this.numServings = numServings;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
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
