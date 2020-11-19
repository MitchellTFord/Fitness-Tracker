package com.fitnesstracker.database;

import androidx.room.ColumnInfo;

import java.util.Objects;
import java.util.Random;


public class NutritionGoal {

    @ColumnInfo(name = "calories_intake")
    private long CaloriesIntake;

    @ColumnInfo(name = "sodium")
    private long Sodium;

    @ColumnInfo(name = "saturated_fat")
    private double SaturatedFat;

    @ColumnInfo(name = "total_sugars")
    private long TotalSugars;

    public NutritionGoal(long CaloriesIntake, long Sodium, double SaturatedFat, long TotalSugars) {
        setCaloriesIntake(CaloriesIntake);
        setSodium(Sodium);
        setSaturatedFat(SaturatedFat);
        setTotalSugars(TotalSugars);
    }

    public static long generateCalories() {
        return new Random().nextLong();
    }

    public long getCaloriesIntake() {
        return CaloriesIntake;
    }

    public void setCaloriesIntake(long CaloriesIntake) {
        this.CaloriesIntake = CaloriesIntake;
    }

    public long getSodium() {
        return Sodium;
    }

    public void setSodium(long Sodium) {
        this.Sodium = Sodium;
    }

    public double getSaturatedFat() {
        return SaturatedFat;
    }

    public void setSaturatedFat(double SaturatedFat) {
        this.SaturatedFat = SaturatedFat;
    }

    public long getTotalSugars() {
        return TotalSugars;
    }

    public void setTotalSugars(long TotalSugars) {
        this.TotalSugars = TotalSugars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NutritionGoal that = (NutritionGoal) o;
        return getCaloriesIntake() == that.getCaloriesIntake() &&
                getSodium() == that.getSodium() &&
                Double.compare(that.getSaturatedFat(), getSaturatedFat()) == 0 &&
                getTotalSugars() == that.getTotalSugars();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCaloriesIntake(), getSodium(), getSaturatedFat(), getTotalSugars());
    }
}

