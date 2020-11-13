package com.fitnesstracker.database;

import androidx.room.TypeConverter;

/**
 * Class containing type converter methods for {@link FTDatabase}.
 */
public class FTTypeConverters {

	@TypeConverter
	public int pass(int num) {
		return num;
	}
}
