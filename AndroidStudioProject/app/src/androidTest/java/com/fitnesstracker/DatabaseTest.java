package com.fitnesstracker;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.fitnesstracker.database.FTDatabase;
import com.fitnesstracker.database.daos.FTDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit test class for ensuring basic functionality of {@link FTDatabase}, {@link FTDao}, and their
 * interactions with their associated entity classes.
 * <p>
 * These tests run on an Android device (or emulator) rather than the developer's computer.
 */
public class DatabaseTest {

	/**
	 * An in-memory database
	 */
	protected FTDatabase db;

	@Before
	public void createDB() {
		Context context = ApplicationProvider.getApplicationContext();
		db = Room.inMemoryDatabaseBuilder(context, FTDatabase.class).build();
	}

	@Test
	public void dbTest_isInitialized() {
		assertNotNull("Database not initialized", db);
	}

	@After
	public void closeDB() {
		db.close();
	}
}
