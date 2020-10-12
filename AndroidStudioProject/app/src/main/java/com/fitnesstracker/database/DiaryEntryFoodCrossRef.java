package com.fitnesstracker.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "diary_food",
        primaryKeys = {"diary_entry_id", "food_id"},
        foreignKeys = {
		        @ForeignKey(entity = DiaryEntry.class,
		                    parentColumns = "id",
		                    childColumns = "diary_entry_id",
		                    onDelete = ForeignKey.CASCADE),
		        @ForeignKey(entity = Food.class,
		                    parentColumns = "id",
		                    childColumns = "food_id",
		                    onDelete = ForeignKey.CASCADE)},
        indices = @Index("food_id"))
public class DiaryEntryFoodCrossRef {
	@ColumnInfo(name = "diary_entry_id")
	private long diaryEntryID;

	@ColumnInfo(name = "food_id")
	private long foodID;

	@ColumnInfo(name = "num_servings")
	private double numServings;

	/**
	 * Constructor that specifies a {@link DiaryEntry}, a {@link Food}, and the number of servings eaten.
	 *
	 * @param diaryEntry the diary entry
	 * @param food the food
	 * @param numServings the number of servings of the food eaten
	 */
	public DiaryEntryFoodCrossRef(@NotNull DiaryEntry diaryEntry, @NotNull Food food, double numServings) {
		this.setDiaryEntryID(diaryEntry.getId());
		this.setFoodID(food.getId());
		this.setNumServings(numServings);
	}

	/**
	 * Constructor that take in IDs rather than references.
	 *
	 * Should only be used by the Room database.
	 *
	 * @param diaryEntryID the ID of the diary entry
	 * @param foodID the ID of the food
	 * @param numServings the number of servings of the food eaten
	 */
	@Deprecated
	public DiaryEntryFoodCrossRef(long diaryEntryID, long foodID, double numServings) {
		this.setDiaryEntryID(diaryEntryID);
		this.setFoodID(foodID);
		this.setNumServings(numServings);
	}

	public long getDiaryEntryID() {
		return diaryEntryID;
	}

	public void setDiaryEntryID(long diaryEntryID) {
		this.diaryEntryID = diaryEntryID;
	}

	public long getFoodID() {
		return foodID;
	}

	public void setFoodID(long foodID) {
		this.foodID = foodID;
	}

	public double getNumServings() {
		return numServings;
	}

	public void setNumServings(double numServings) {
		assert numServings >= 0 : "numServings must be greater than or equal to zero.";
		this.numServings = numServings;
	}

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DiaryEntryFoodCrossRef that = (DiaryEntryFoodCrossRef) o;
		return getDiaryEntryID() == that.getDiaryEntryID() &&
				getFoodID() == that.getFoodID() &&
				Double.compare(that.getNumServings(), getNumServings()) == 0;
	}
}
