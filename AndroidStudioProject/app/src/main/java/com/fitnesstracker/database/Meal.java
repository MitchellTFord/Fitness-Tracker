package com.fitnesstracker.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Relation;

import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class Meal {

	@Embedded
	private FoodDiaryEntry foodDiaryEntry;

	@Relation(
			parentColumn = "food_id",
			entityColumn = "id"
	)
	private Food food;

	public Meal(FoodDiaryEntry foodDiaryEntry, Food food) {
		setFoodDiaryEntry(foodDiaryEntry);
		setFood(food);
	}

	public FoodDiaryEntry getFoodDiaryEntry() {
		return foodDiaryEntry;
	}

	public void setFoodDiaryEntry(FoodDiaryEntry foodDiaryEntry) {
		if(foodDiaryEntry == null) {
			throw new IllegalArgumentException("foodDiaryEntry must not be null.");
		}

		this.foodDiaryEntry = foodDiaryEntry;
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

	@Override
	public String toString() {
		return "Meal{" +
				"foodDiaryEntry=" + foodDiaryEntry +
				", food=" + food +
				'}';
	}
}
