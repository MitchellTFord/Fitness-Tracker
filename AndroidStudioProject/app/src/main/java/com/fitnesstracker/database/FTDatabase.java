package com.fitnesstracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Food.class, DiaryEntry.class, DiaryEntryFoodCrossRef.class},
          version = 1,
          exportSchema = false)
@TypeConverters({DatabaseTypeConverters.class})
public abstract class FTDatabase extends RoomDatabase {
	public abstract FTDao ftDao();

	private static volatile FTDatabase INSTANCE;
	private static final int NUMBER_OF_THREADS = 4;
	public static final ExecutorService executor =
			Executors.newFixedThreadPool(NUMBER_OF_THREADS);

	public static FTDatabase getDatabase(final Context context) {
		if (INSTANCE == null) {
			synchronized (FTDatabase.class) {
				INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FTDatabase.class,
						"ft_database").build();
			}
		}
		return INSTANCE;
	}
}
