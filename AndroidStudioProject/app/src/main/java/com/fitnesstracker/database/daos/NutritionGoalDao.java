package com.fitnesstracker.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.fitnesstracker.database.entities.NutritionGoal;

import java.util.List;

@Dao
public abstract class NutritionGoalDao extends FTDao<NutritionGoal> {
    @Query("SELECT * FROM nutrition_goal" +
            " ORDER BY nutrient DESC")
    public abstract List<NutritionGoal> getAll();

    @Query("SELECT * FROM nutrition_goal" +
            " ORDER BY nutrient DESC")
    public abstract LiveData<List<NutritionGoal>> getAllLD();

    @Query("SELECT * FROM nutrition_goal" + " WHERE nutrient = :nutrient ")
    public abstract NutritionGoal get(int nutrient);

    @Query("SELECT * FROM nutrition_goal" + " WHERE nutrient = :nutrient ")
    public abstract LiveData<NutritionGoal> getLD(int nutrient);
}
