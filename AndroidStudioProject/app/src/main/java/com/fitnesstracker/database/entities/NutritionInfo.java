package com.fitnesstracker.database.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import java.util.Random;

/**
 * Class representing the nutrition facts of a serving of food or a combination thereof.
 *
 * @author Mitchell Ford
 */
public class NutritionInfo implements Parcelable {

	/** The number of calories (kcal) */
	@ColumnInfo(name = "calories")
	private int calories; //kcal

	/** The number of calories (kcal) from fat */
	@ColumnInfo(name = "fat_calories")
	private int fatCalories; //kcal

	/** The amount of fat in grams */
	@ColumnInfo(name = "total_fat")
	private int totalFat; //g

	/** The amount of saturated fat in grams */
	@ColumnInfo(name = "saturated_fat")
	private int saturatedFat; //g

	/** The amount of trans fat in grams */
	@ColumnInfo(name = "trans_fat")
	private int transFat; //g

	/** The amount of cholesterol in milligrams */
	@ColumnInfo(name = "cholesterol")
	private int cholesterol; //mg

	/** The amount of sodium in milligrams */
	@ColumnInfo(name = "sodium")
	private int sodium; //mg

	/** The total amount of carbohydrates in grams */
	@ColumnInfo(name = "total_carbs")
	private int totalCarbs; //g

	/** The amount of dietary fiber in grams */
	@ColumnInfo(name = "dietary_fiber")
	private int dietaryFiber; //g

	/** The total amount of sugar in grams */
	@ColumnInfo(name = "total_sugars")
	private int totalSugars; //g

	/** The amount of added sugars in grams */
	@ColumnInfo(name = "added_sugars")
	private int addedSugars; //g

	/** The amount of protein in grams */
	@ColumnInfo(name = "protein")
	private int protein; //g

	/** The amount of vitamin D in micrograms */
	@ColumnInfo(name = "vitamin_d")
	private int vitaminD;

	/** The amount of calcium in milligrams */
	@ColumnInfo(name = "calcium")
	private int calcium;

	/** The amount of iron in milligrams */
	@ColumnInfo(name = "iron")
	private int iron;

	/** The amount of potassium in milligrams */
	@ColumnInfo(name = "potassium")
	private int potassium; //mg

	/**
	 * Default constructor, leaves all attributes with their default values.
	 */
	public NutritionInfo() {
	}

	/**
	 * Constructor that copies the attributes of another NutritionInfo object.
	 *
	 * @param other the object to copy data from
	 */
	public NutritionInfo(@NonNull NutritionInfo other) {
		this.setCalories(other.getCalories());
		this.setFatCalories(other.getFatCalories());
		this.setTotalFat(other.getTotalFat());
		this.setSaturatedFat(other.getSaturatedFat());
		this.setTransFat(other.getTransFat());
		this.setCholesterol(other.getCholesterol());
		this.setSodium(other.getSodium());
		this.setTotalCarbs(other.getTotalCarbs());
		this.setDietaryFiber(other.getDietaryFiber());
		this.setTotalSugars(other.getTotalSugars());
		this.setAddedSugars(other.getAddedSugars());
		this.setProtein(other.getProtein());
		this.setVitaminD(other.getVitaminD());
		this.setCalcium(other.getCalcium());
		this.setIron(other.getIron());
		this.setPotassium(other.getPotassium());
	}

	/**
	 * Constructor reads data from a {@link Parcel}.
	 *
	 * @param in the parcel to read from
	 */
	protected NutritionInfo(@NonNull Parcel in) {
		calories = in.readInt();
		fatCalories = in.readInt();
		totalFat = in.readInt();
		saturatedFat = in.readInt();
		transFat = in.readInt();
		cholesterol = in.readInt();
		sodium = in.readInt();
		totalCarbs = in.readInt();
		dietaryFiber = in.readInt();
		totalSugars = in.readInt();
		addedSugars = in.readInt();
		protein = in.readInt();
		vitaminD = in.readInt();
		calcium = in.readInt();
		iron = in.readInt();
		potassium = in.readInt();
	}

	/**
	 * A {@link android.os.Parcelable.Creator} of NutritionInfo objects from {@link Parcel} objects.
	 */
	public static final Creator<NutritionInfo> CREATOR = new Creator<NutritionInfo>() {
		@Override
		public NutritionInfo createFromParcel(Parcel in) {
			return new NutritionInfo(in);
		}

		@Override
		public NutritionInfo[] newArray(int size) {
			return new NutritionInfo[size];
		}
	};

	/**
	 * Create a new NutritionInfo object with randomly assigned attributes.
	 *
	 * @return the new object
	 */
	public static NutritionInfo makeRandom() {
		NutritionInfo nutritionInfo = new NutritionInfo();
		Random random = new Random();

		nutritionInfo.setCalories(random.nextInt(1000));
		nutritionInfo.setFatCalories(random.nextInt(1000));
		nutritionInfo.setTotalFat(random.nextInt(500));
		nutritionInfo.setSaturatedFat(random.nextInt(500));
		nutritionInfo.setTransFat(random.nextInt(500));
		nutritionInfo.setCholesterol(random.nextInt(500));
		nutritionInfo.setSodium(random.nextInt(2500));
		nutritionInfo.setTotalCarbs(random.nextInt(500));
		nutritionInfo.setDietaryFiber(random.nextInt(500));
		nutritionInfo.setTotalSugars(random.nextInt(500));
		nutritionInfo.setAddedSugars(random.nextInt(500));
		nutritionInfo.setProtein(random.nextInt(500));
		nutritionInfo.setVitaminD(random.nextInt(500));
		nutritionInfo.setCalcium(random.nextInt(500));
		nutritionInfo.setIron(random.nextInt(500));
		nutritionInfo.setPotassium(random.nextInt(500));

		return nutritionInfo;
	}

	/**
	 * Get the sum of one or more NutritionInfo objects.
	 *
	 * <p>Note: does not modify any of the objects passed as arguments.</p>
	 *
	 * <p>Implemented as repeated calls to <code>add</code>.</p>
	 *
	 * @param items the NutritionInfo objects to add together
	 *
	 * @return a NutritionInfo object with attributes equal
	 */
	public static NutritionInfo sum(@NonNull NutritionInfo... items) {
		NutritionInfo out = new NutritionInfo();
		for (NutritionInfo item : items) {
			out.add(item);
		}
		return out;
	}

	/**
	 * Create a new NutritionInfo object with attributes equal to the sum of the attributes in
	 * <code>this</code> and <code>other</code>.
	 *
	 * <p><code>a.plus(b)</code> is analogous to <code>a + b</code> for numbers</p>
	 *
	 * <br>This method is equivalent to the following code block:
	 * <pre>
	 * 	NutritionInfo nutritionInfo = new NutritionInfo(this);
	 * 	nutritionInfo.add(other);
	 * </pre>
	 *
	 * @param other the other object to sum with this one
	 *
	 * @return the new NutritionInfo object
	 */
	public NutritionInfo plus(@NonNull NutritionInfo other) {
		NutritionInfo nutritionInfo = new NutritionInfo();
		nutritionInfo.add(other);
		return nutritionInfo;
	}

	/**
	 * Add the values of each of another NutritionInfo's fields to this one's.
	 *
	 * <p><code>a.add(b)</code> is analogous to <code>a += b</code></p>
	 *
	 * <p>Note: this method modifies this object but does not modify <code>other</code></p>.
	 *
	 * @param other the NutritionInfo object to get values from
	 */
	public void add(@NonNull NutritionInfo other) {
		this.setCalories(this.getCalories() + other.getCalories());
		this.setFatCalories(this.getFatCalories() + other.getFatCalories());
		this.setTotalFat(this.getTotalFat() + other.getTotalFat());
		this.setSaturatedFat(this.getSaturatedFat() + other.getSaturatedFat());
		this.setTransFat(this.getTransFat() + other.getTransFat());
		this.setCholesterol(this.getCholesterol() + other.getCholesterol());
		this.setSodium(this.getSodium() + other.getSodium());
		this.setTotalCarbs(this.getTotalCarbs() + other.getTotalCarbs());
		this.setDietaryFiber(this.getDietaryFiber() + other.getDietaryFiber());
		this.setTotalSugars(this.getTotalSugars() + other.getTotalSugars());
		this.setAddedSugars(this.getAddedSugars() + other.getAddedSugars());
		this.setProtein(this.getProtein() + other.getProtein());
		this.setVitaminD(this.getVitaminD() + other.getVitaminD());
		this.setCalcium(this.getCalcium() + other.getCalcium());
		this.setIron(this.getIron() + other.getIron());
		this.setPotassium(this.getPotassium() + other.getPotassium());
	}

	/**
	 * Creates a new NutritionInfo object with fields equal to those of this one multiplied by a
	 * scalar quantity.
	 *
	 * <p>Note: this method does not modify the object it is a member of</p>
	 *
	 * <br>This method is equivalent to the following code block:
	 * <pre>
	 *  NutritionInfo nutritionInfo = new NutritionInfo(this);
	 *  nutritionInfo.mul(scalar);
	 * </pre>
	 *
	 * @param scalar the scalar to multiply each field by
	 *
	 * @return the new NutritionInfo object
	 */
	public NutritionInfo times(double scalar) {
		NutritionInfo nutritionInfo = new NutritionInfo(this);
		nutritionInfo.mul(scalar);
		return nutritionInfo;
	}

	/**
	 * Multiply each of the nutrients in this object by a scalar quantity.
	 *
	 * @param scalar the scalar to multiply each field by
	 */
	public void mul(double scalar) {
		this.setCalories((int) (this.getCalories()*scalar));
		this.setFatCalories((int) (this.getFatCalories()*scalar));
		this.setTotalFat((int) (this.getTotalFat()*scalar));
		this.setSaturatedFat((int) (this.getSaturatedFat()*scalar));
		this.setTransFat((int) (this.getTransFat()*scalar));
		this.setCholesterol((int) (this.getCholesterol()*scalar));
		this.setSodium((int) (this.getSodium()*scalar));
		this.setTotalCarbs((int) (this.getTotalCarbs()*scalar));
		this.setDietaryFiber((int) (this.getDietaryFiber()*scalar));
		this.setTotalSugars((int) (this.getTotalSugars()*scalar));
		this.setAddedSugars((int) (this.getAddedSugars()*scalar));
		this.setProtein((int) (this.getProtein()*scalar));
		this.setVitaminD((int) (this.getVitaminD()*scalar));
		this.setCalcium((int) (this.getCalcium()*scalar));
		this.setIron((int) (this.getIron()*scalar));
		this.setPotassium((int) (this.getPotassium()*scalar));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		NutritionInfo that = (NutritionInfo) o;
		return getCalories() == that.getCalories() &&
				getFatCalories() == that.getFatCalories() &&
				getTotalFat() == that.getTotalFat() &&
				getSaturatedFat() == that.getSaturatedFat() &&
				getTransFat() == that.getTransFat() &&
				getCholesterol() == that.getCholesterol() &&
				getSodium() == that.getSodium() &&
				getTotalCarbs() == that.getTotalCarbs() &&
				getDietaryFiber() == that.getDietaryFiber() &&
				getTotalSugars() == that.getTotalSugars() &&
				getAddedSugars() == that.getAddedSugars() &&
				getProtein() == that.getProtein() &&
				getVitaminD() == that.getVitaminD() &&
				getCalcium() == that.getCalcium() &&
				getIron() == that.getIron() &&
				getPotassium() == that.getPotassium();
	}

	/** The number of calories (kcal) */
	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	/** The number of calories (kcal) from fat */
	public int getFatCalories() {
		return fatCalories;
	}

	public void setFatCalories(int fatCalories) {
		this.fatCalories = fatCalories;
	}

	/** The amount of fat in grams */
	public int getTotalFat() {
		return totalFat;
	}

	public void setTotalFat(int totalFat) {
		this.totalFat = totalFat;
	}

	/** The amount of saturated fat in grams */
	public int getSaturatedFat() {
		return saturatedFat;
	}

	public void setSaturatedFat(int saturatedFat) {
		this.saturatedFat = saturatedFat;
	}

	/** The amount of trans fat in grams */
	public int getTransFat() {
		return transFat;
	}

	public void setTransFat(int transFat) {
		this.transFat = transFat;
	}

	/** The amount of cholesterol in milligrams */
	public int getCholesterol() {
		return cholesterol;
	}

	public void setCholesterol(int cholesterol) {
		this.cholesterol = cholesterol;
	}

	/** The amount of sodium in milligrams */
	public int getSodium() {
		return sodium;
	}

	public void setSodium(int sodium) {
		this.sodium = sodium;
	}

	/** The total amount of carbohydrates in grams */
	public int getTotalCarbs() {
		return totalCarbs;
	}

	public void setTotalCarbs(int totalCarbs) {
		this.totalCarbs = totalCarbs;
	}

	/** The amount of dietary fiber in grams */
	public int getDietaryFiber() {
		return dietaryFiber;
	}

	public void setDietaryFiber(int dietaryFiber) {
		this.dietaryFiber = dietaryFiber;
	}

	/** The total amount of sugar in grams */
	public int getTotalSugars() {
		return totalSugars;
	}

	public void setTotalSugars(int totalSugars) {
		this.totalSugars = totalSugars;
	}

	/** The amount of added sugars in grams */
	public int getAddedSugars() {
		return addedSugars;
	}

	public void setAddedSugars(int addedSugars) {
		this.addedSugars = addedSugars;
	}

	/** The amount of protein in grams */
	public int getProtein() {
		return protein;
	}

	public void setProtein(int protein) {
		this.protein = protein;
	}

	/** The amount of vitamin D in micrograms */
	public int getVitaminD() {
		return vitaminD;
	}

	public void setVitaminD(int vitaminD) {
		this.vitaminD = vitaminD;
	}

	/** The amount of calcium in milligrams */
	public int getCalcium() {
		return calcium;
	}

	public void setCalcium(int calcium) {
		this.calcium = calcium;
	}

	/** The amount of iron in milligrams */
	public int getIron() {
		return iron;
	}

	public void setIron(int iron) {
		this.iron = iron;
	}

	/** The amount of potassium in milligrams */
	public int getPotassium() {
		return potassium;
	}

	public void setPotassium(int potassium) {
		this.potassium = potassium;
	}

	/**
	 * Describe the kinds of special objects contained in this Parcelable instance's marshaled
	 * representation. For example, if the object will include a file descriptor in the output of
	 * {@link #writeToParcel(Parcel, int)}, the return value of this method must include the {@link
	 * #CONTENTS_FILE_DESCRIPTOR} bit.
	 *
	 * @return a bitmask indicating the set of special object types marshaled by this Parcelable
	 * object instance.
	 */
	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 * Flatten this object in to a Parcel.
	 *
	 * @param dest  The Parcel in which the object should be written.
	 * @param flags Additional flags about how the object should be written. May be 0 or {@link
	 *              #PARCELABLE_WRITE_RETURN_VALUE}.
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(calories);
		dest.writeInt(fatCalories);
		dest.writeInt(totalFat);
		dest.writeInt(saturatedFat);
		dest.writeInt(transFat);
		dest.writeInt(cholesterol);
		dest.writeInt(sodium);
		dest.writeInt(totalCarbs);
		dest.writeInt(dietaryFiber);
		dest.writeInt(totalSugars);
		dest.writeInt(addedSugars);
		dest.writeInt(protein);
		dest.writeInt(vitaminD);
		dest.writeInt(calcium);
		dest.writeInt(iron);
		dest.writeInt(potassium);
	}
}
