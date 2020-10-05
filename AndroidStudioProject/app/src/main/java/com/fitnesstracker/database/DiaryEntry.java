package com.fitnesstracker.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Random;

@Entity(tableName = "diary_entry",
        primaryKeys = "id")
public class DiaryEntry {
	/** A randomly assigned unique identifier for this entry */
	@ColumnInfo(name = "id")
	private long id;

	/** The date this entry takes place on. */
	@ColumnInfo(name = "date")
	private Date date;

	public DiaryEntry(Date date) {
		Random rand = new Random();
		this.setId(rand.nextLong());
		this.setDate(date);
	}

	public long getId() {
		return id;
	}

	/**
	 * Set the id of this {@link DiaryEntry} object.
	 * <p>
	 * This method should only be invoked by the Room database.
	 *
	 * @param id the id to give this object
	 */
	@Deprecated
	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(@NotNull Date date) {
		this.date = date;
	}

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DiaryEntry that = (DiaryEntry) o;
		return getId() == that.getId() &&
				getDate().equals(that.getDate());
	}
}
