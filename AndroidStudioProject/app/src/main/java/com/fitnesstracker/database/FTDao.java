package com.fitnesstracker.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A data access object to be used with {@link FTDatabase}.
 *
 * @author Mitchell Ford
 */
@Dao
public interface FTDao
{
    /**
     * Query the database for a Food with a given id.
     *
     * @param id the id of the Food to search for
     * @return the Food with the given id, or null if no such Food exists
     */
    @Nullable
    @Query("SELECT * FROM Food WHERE Food.id = :id")
    public Food getFood(@NotNull long id);

    @Query("SELECT * FROM Food WHERE Food.name LIKE :name")
    public List<Food> getFood(@NotNull String name);

    /**
     * Query the database for a {@link java.util.List} of all the Foods it has.
     *
     * @return a {@link List} of all the Foods in the database
     */
    @Query("SELECT * FROM Food")
    public List<Food> getAllFoods();

    /**
     * Insert Food objects into the database.
     *
     * @param foods one or more Food objects to insert into the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(@NotNull Food... foods);
}
