package com.fitnesstracker.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * Class representing a food item.
 * <p>
 * Consists of a unique ID, a name, a serving size, and all the necessary nutrition facts.
 */
@Entity(tableName = "food",
        primaryKeys = "id")
public class Food {

	private static final String[] SAMPLE_NAMES = new String[]{"Apple", "Orange", "Banana", "Egg",
			"Potato", "Bread", "Cheese"};

	private static final String[] SAMPLE_SERVING_UNITS = new String[]{"grams", "cups", "oz", "mL",
			"lbs"};

	/** A randomly assigned identifier for this Food. */
	@ColumnInfo(name = "id")
	private long id;

	/** The name of this Food. */
	@ColumnInfo(name = "name")
	private String name;

	/** The numeric part of a serving listing. */
	@ColumnInfo(name = "serving_size")
	private double servingSize;

	/** The non-numeric part of a serving listing. */
	@ColumnInfo(name = "serving_unit")
	private String servingUnit;

	/** The nutrition facts of one serving of this food. */
	@Embedded(prefix = "nutrition_")
	private NutritionInfo nutritionInfo;

	/**
	 * Constructor that specifies a name and assigns a random ID.
	 * <p>
	 * Creates a {@link NutritionInfo} object with default fields.
	 *
	 * @param name
	 */
	public Food(@NotNull String name, @NotNull String servingUnit, @NotNull Double servingSize) {
		Random rand = new Random();
		this.setId(rand.nextLong());
		this.setName(name);
		this.setServingUnit(servingUnit);
		this.setServingSize(servingSize);

		// Create a default NutritionInfo object
		this.setNutritionInfo(new NutritionInfo());
	}

	/**
	 * Create a new Food object with a name randomly selected from {@link Food#SAMPLE_NAMES}, a
	 * serving unit randomly selected from {@link Food#SAMPLE_SERVING_UNITS}, a randomly generated
	 * serving size, and a {@link Food#nutritionInfo} generated using {@link
	 * NutritionInfo#makeRandom()}.
	 *
	 * @return the newly created Food object
	 */
	public static Food makeRandom() {
		Random random = new Random();
		Food food = new Food(
				SAMPLE_NAMES[random.nextInt(SAMPLE_NAMES.length)],
				SAMPLE_SERVING_UNITS[random.nextInt(SAMPLE_SERVING_UNITS.length)],
				random.nextInt(1000)*random.nextDouble()
		);
		food.setNutritionInfo(NutritionInfo.makeRandom());
		return food;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Food food = (Food) o;
		return getId() == food.getId() &&
				Double.compare(food.getServingSize(), getServingSize()) == 0 &&
				getName().equals(food.getName()) &&
				getServingUnit().equals(food.getServingUnit()) &&
				getNutritionInfo().equals(food.getNutritionInfo());
	}

	public long getId() {
		return id;
	}

	/**
	 * Set the id of this Food object.
	 * <p>
	 * This method should only be called by the Room database.
	 *
	 * @param id
	 */
	@Deprecated
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(@NotNull String name) {
		this.name = name;
	}

	public double getServingSize() {
		return servingSize;
	}

	public void setServingSize(Double servingSize) {
		if (servingSize == null || servingSize < 0) {
			throw new IllegalArgumentException("Serving size must be greater than or equal to zero");
		}
		this.servingSize = servingSize;
	}

	public String getServingUnit() {
		return servingUnit;
	}

	public void setServingUnit(@NotNull String servingUnit) {
		this.servingUnit = servingUnit;
	}

	public NutritionInfo getNutritionInfo() {
		return nutritionInfo;
	}

	public void setNutritionInfo(@NotNull NutritionInfo nutritionInfo) {
		this.nutritionInfo = nutritionInfo;
	}

	@Override
	public String toString() {
		return "Food{" +
				"id=" + id +
				", name='" + name + '\'' +
				", servingSize=" + servingSize +
				", servingUnit='" + servingUnit + '\'' +
				", nutritionInfo=" + nutritionInfo +
				'}';
	}

	/**
	 * Class representing the nutrition facts of a serving of food or a combination thereof.
	 *
	 * @author Mitchell Ford
	 */
	public static class NutritionInfo {

		/** The number of calories (kcal) */
		@ColumnInfo(name = "calories")
		public int calories; //kcal

		/** The number of calories (kcal) from fat */
		@ColumnInfo(name = "fat_calories")
		public int fatCalories; //kcal

		/** The amount of fat in grams */
		@ColumnInfo(name = "total_fat")
		public int totalFat; //g

		/** The amount of saturated fat in grams */
		@ColumnInfo(name = "saturated_fat")
		public int saturatedFat; //g

		/** The amount of trans fat in grams */
		@ColumnInfo(name = "trans_fat")
		public int transFat; //g

		/** The amount of cholesterol in milligrams */
		@ColumnInfo(name = "cholesterol")
		public int cholesterol; //mg

		/** The amount of sodium in milligrams */
		@ColumnInfo(name = "sodium")
		public int sodium; //mg

		/** The total amount of carbohydrates in grams */
		@ColumnInfo(name = "total_carbs")
		public int totalCarbs; //g

		/** The amount of dietary fiber in grams */
		@ColumnInfo(name = "dietary_fiber")
		public int dietaryFiber; //g

		/** The total amount of sugar in grams */
		@ColumnInfo(name = "total_sugars")
		public int totalSugars; //g

		/** The amount of added sugars in grams */
		@ColumnInfo(name = "added_sugars")
		public int addedSugars; //g

		/** The amount of protein in grams */
		@ColumnInfo(name = "protein")
		public int protein; //g

		/** The amount of vitamin D in micrograms */
		@ColumnInfo(name = "vitamin_d")
		public int vitaminD;

		/** The amount of calcium in milligrams */
		@ColumnInfo(name = "calcium")
		public int calcium;

		/** The amount of iron in milligrams */
		@ColumnInfo(name = "iron")
		public int iron;

		/** The amount of potassium in milligrams */
		@ColumnInfo(name = "potassium")
		public int potassium; //mg

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
		public NutritionInfo(@NotNull Food.NutritionInfo other) {
			this.calories = other.calories;
			this.fatCalories = other.fatCalories;
			this.totalFat = other.totalFat;
			this.saturatedFat = other.saturatedFat;
			this.transFat = other.transFat;
			this.cholesterol = other.cholesterol;
			this.sodium = other.sodium;
			this.totalCarbs = other.totalCarbs;
			this.dietaryFiber = other.dietaryFiber;
			this.totalSugars = other.totalSugars;
			this.addedSugars = other.addedSugars;
			this.protein = other.protein;
			this.vitaminD = other.vitaminD;
			this.calcium = other.calcium;
			this.iron = other.iron;
			this.potassium = other.potassium;
		}

		/**
		 * Create a new NutritionInfo object with randomly assigned attributes.
		 *
		 * @return the new object
		 */
		public static NutritionInfo makeRandom() {
			NutritionInfo nutritionInfo = new NutritionInfo();
			Random random = new Random();

			nutritionInfo.calories = random.nextInt(1000);
			nutritionInfo.fatCalories = random.nextInt(1000);
			nutritionInfo.totalFat = random.nextInt(500);
			nutritionInfo.saturatedFat = random.nextInt(500);
			nutritionInfo.transFat = random.nextInt(500);
			nutritionInfo.cholesterol = random.nextInt(500);
			nutritionInfo.sodium = random.nextInt(2500);
			nutritionInfo.totalCarbs = random.nextInt(500);
			nutritionInfo.dietaryFiber = random.nextInt(500);
			nutritionInfo.totalSugars = random.nextInt(500);
			nutritionInfo.addedSugars = random.nextInt(500);
			nutritionInfo.protein = random.nextInt(500);
			nutritionInfo.vitaminD = random.nextInt(500);
			nutritionInfo.calcium = random.nextInt(500);
			nutritionInfo.iron = random.nextInt(500);
			nutritionInfo.potassium = random.nextInt(500);

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
		public static NutritionInfo sum(@NotNull NutritionInfo... items) {
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
		public NutritionInfo plus(@NotNull Food.NutritionInfo other) {
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
		public void add(@NotNull Food.NutritionInfo other) {
			this.calories += other.calories;
			this.fatCalories += other.fatCalories;
			this.totalFat += other.totalFat;
			this.saturatedFat += other.saturatedFat;
			this.transFat += other.transFat;
			this.cholesterol += other.cholesterol;
			this.sodium += other.sodium;
			this.totalCarbs += other.totalCarbs;
			this.dietaryFiber += other.dietaryFiber;
			this.totalSugars += other.totalSugars;
			this.addedSugars += other.addedSugars;
			this.protein += other.protein;
			this.vitaminD += other.vitaminD;
			this.calcium += other.calcium;
			this.iron += other.iron;
			this.potassium += other.potassium;
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
			this.calories *= scalar;
			this.fatCalories *= scalar;
			this.totalFat *= scalar;
			this.saturatedFat *= scalar;
			this.transFat *= scalar;
			this.cholesterol *= scalar;
			this.sodium *= scalar;
			this.totalCarbs *= scalar;
			this.dietaryFiber *= scalar;
			this.totalSugars *= scalar;
			this.addedSugars *= scalar;
			this.protein *= scalar;
			this.vitaminD *= scalar;
			this.calcium *= scalar;
			this.iron *= scalar;
			this.potassium *= scalar;
		}

		@Override public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			NutritionInfo that = (NutritionInfo) o;
			return calories == that.calories &&
					fatCalories == that.fatCalories &&
					totalFat == that.totalFat &&
					saturatedFat == that.saturatedFat &&
					transFat == that.transFat &&
					cholesterol == that.cholesterol &&
					sodium == that.sodium &&
					totalCarbs == that.totalCarbs &&
					dietaryFiber == that.dietaryFiber &&
					totalSugars == that.totalSugars &&
					addedSugars == that.addedSugars &&
					protein == that.protein &&
					vitaminD == that.vitaminD &&
					calcium == that.calcium &&
					iron == that.iron &&
					potassium == that.potassium;
		}
	}
}
