package com.fitnesstracker.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Food")
public class Food
{
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

    /** The number of calories (kcal) in one serving of this Food */
    @ColumnInfo(name = "calories")
    public int calories;

    //TODO: Add the rest of the nutrition information
}
