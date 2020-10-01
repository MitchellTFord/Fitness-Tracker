package com.fitnesstracker.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Food.class}, version = 1, exportSchema = false)
public abstract class FTDatabase extends RoomDatabase
{
    public abstract FTDao ftDao();
}
