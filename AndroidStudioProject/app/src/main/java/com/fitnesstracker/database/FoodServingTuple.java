package com.fitnesstracker.database;

import androidx.room.Embedded;

import org.jetbrains.annotations.NotNull;

/**
 * A simple class for storing the results of {@link FTDao#getFoodsFromDiary(long)}.
 */
public class FoodServingTuple {

	@Embedded
	private Food food;

	private double numServings;

	public FoodServingTuple(@NotNull Food food, double numServings) {
		this.setFood(food);
		this.setNumServings(numServings);
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public double getNumServings() {
		return numServings;
	}

	public void setNumServings(double numServings) {
		if (numServings < 0) {
			throw new IllegalArgumentException("numServings must be greater than or equal to zero.");
		}
		this.numServings = numServings;
	}
}
