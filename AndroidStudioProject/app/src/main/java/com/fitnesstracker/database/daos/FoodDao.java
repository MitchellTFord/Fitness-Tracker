package com.fitnesstracker.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.fitnesstracker.database.Food;

import java.util.List;

@Dao
public abstract class FoodDao extends FTDao<Food> {

	@Query("SELECT * FROM food")
	public abstract List<Food> getAll();

	@Query("SELECT * FROM food")
	public abstract LiveData<List<Food>> getAllLD();

	@Query("SELECT * FROM food WHERE id = :id")
	public abstract Food get(long id);

	@Query("SELECT * FROM food WHERE id = :id")
	public abstract LiveData<Food> getLD(long id);

	@Query("SELECT * FROM food WHERE name LIKE :name")
	public abstract List<Food> get(String name);

	@Query("SELECT * FROM food WHERE name LIKE :name")
	public abstract LiveData<List<Food>> getLD(String name);
}
