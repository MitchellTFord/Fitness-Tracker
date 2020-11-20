package com.fitnesstracker.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "nutrition_goal")


public class NutritionGoal {
    public static final int CALORIES = 0;
    @ColumnInfo(name = "nutrient")
    @PrimaryKey
    private int nutrient;
    @ColumnInfo(name = "amount")
    private int amount;

    public NutritionGoal(int nutrient, int amount) {
        setNutrient(nutrient);
        setAmount(amount);
    }

    public int getNutrient() {
        return nutrient;
    }

    public void setNutrient(int nutrient) {
        this.nutrient = nutrient;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be > or equal to 0");
        }
        this.amount = amount;
    }


}





