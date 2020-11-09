package com.fitnesstracker.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Ignore;

import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class Meal {

	@ColumnInfo(name = "id")
	private long id;

	@Embedded(prefix = "food_")
	private Food food;

	@ColumnInfo(name = "num_servings")
	private double numServings;

	@ColumnInfo(name = "time")
	private long time;

	@Ignore
	private Date date;

	public Meal(long id, @NonNull Food food, double numServings, long time) {
		setId(id);
		setFood(food);
		setNumServings(numServings);
		setTime(time);
	}

	@Ignore
	public Meal(@NonNull Food food, double numServings, long time) {
		this(generateId(), food, numServings, time);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public static long generateId() {
		return new Random().nextLong();
	}

	public double getNumServings() {
		return numServings;
	}

	public void setNumServings(double numServings) {
		if(numServings < 0) {
			throw new IllegalArgumentException("numServings must be greater than or equal to 0.");
		}

		this.numServings = numServings;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		if(time < 0) {
			throw new IllegalArgumentException("time must be greater than or equal to 0.");
		}

		this.time = time;
		this.date = null;
	}

	public Date getTimeAsDate() {
		if(date == null) {
			date = new Date(time);
		}

		return date;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		if(food == null) {
			throw new IllegalArgumentException("food must not be null.");
		}

		this.food = food;
	}

	public FoodDiaryEntry getDiaryEntry() {
		return new FoodDiaryEntry(getId(), food.getId(), getNumServings(), getTime());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Meal meal = (Meal) o;
		return getId() == meal.getId() &&
				Double.compare(meal.getNumServings(), getNumServings()) == 0 &&
				getTime() == meal.getTime() &&
				Objects.equals(getFood(), meal.getFood());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getNumServings(), getTime(), getFood());
	}
}
