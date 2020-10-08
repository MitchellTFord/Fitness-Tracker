package com.fitnesstracker.database;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import java.util.Date;

public class DatabaseTypeConverters {
	/**
	 * Convert a long timestamp to a {@link Date}
	 *
	 * @param value the number of milliseconds since the epoch
	 *
	 * @return the date this timestamp lies in
	 */
	@TypeConverter
	@Nullable
	public static Date fromTimestamp(Long value) {
		return value == null ? null : new Date(value);
	}

	/**
	 * Convert a {@link Date} to a long timestamp.
	 *
	 * @param date the date to convert
	 *
	 * @return a long timestamp that lies on the given date
	 */
	@TypeConverter
	@Nullable
	public static Long dateToTimestamp(Date date) {
		return date == null ? null : date.getTime();
	}
}
