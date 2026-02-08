package com.example.recipemaster.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Recipe entity representing a single recipe in the database
 * Contains all information about a recipe including ingredients, instructions, and nutrition
 *
 * @property id Unique identifier for the recipe (auto-generated)
 * @property name Recipe name/title
 * @property description Brief description of the recipe
 * @property category Recipe category (Breakfast, Lunch, etc.)
 * @property cuisine Type of cuisine (Italian, Chinese, etc.)
 * @property difficulty Recipe difficulty level (Easy, Medium, Hard)
 * @property prepTimeMin Preparation time in minutes
 * @property cookTimeMin Cooking time in minutes
 * @property totalTimeMin Total time (prep + cook) in minutes
 * @property servings Number of servings this recipe makes
 * @property ingredients Newline-separated list of ingredients
 * @property instructions Newline-separated cooking instructions
 * @property tags Comma-separated tags for searching
 * @property imageUrl Path to recipe image (stored in internal storage)
 * @property rating User rating (0.0 to 5.0 stars)
 * @property isFavorite Whether this recipe is marked as favorite
 * @property timesCooked Number of times this recipe has been cooked
 * @property lastCooked Timestamp when recipe was last cooked (milliseconds)
 * @property notes User's personal notes about the recipe
 * @property dateCreated Timestamp when recipe was created (milliseconds)
 * @property dateModified Timestamp when recipe was last modified (milliseconds)
 * @property caloriesKcal Calories per serving in kcal
 * @property proteinG Protein content per serving in grams
 * @property carbsG Carbohydrate content per serving in grams
 * @property fatG Fat content per serving in grams
 *
 * @author Heavenlight Mhally
 */
@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val description: String = "",
    val category: String, // RecipeCategory.displayName
    val cuisine: String = "",
    val difficulty: String = "", // DifficultyLevel.displayName

    val prepTimeMin: Int = 0,
    val cookTimeMin: Int = 0,
    val totalTimeMin: Int = 0,
    val servings: Int = 1,

    val ingredients: String = "", // Newline-separated
    val instructions: String = "", // Newline-separated
    val tags: String = "", // Comma-separated

    val imageUrl: String? = null,
    val rating: Float = 0f,
    val isFavorite: Boolean = false,

    val timesCooked: Int = 0,
    val lastCooked: Long? = null,
    val notes: String = "",

    val dateCreated: Long = System.currentTimeMillis(),
    val dateModified: Long = System.currentTimeMillis(),

    // Nutrition information
    val caloriesKcal: Int = 0,
    val proteinG: Float = 0f,
    val carbsG: Float = 0f,
    val fatG: Float = 0f
) {
    /**
     * Get ingredients as a list
     * @return List of individual ingredients
     */
    fun getIngredientsList(): List<String> {
        return if (ingredients.isBlank()) emptyList()
        else ingredients.split("\n").filter { it.isNotBlank() }
    }

    /**
     * Get instructions as a list of steps
     * @return List of instruction steps
     */
    fun getInstructionsList(): List<String> {
        return if (instructions.isBlank()) emptyList()
        else instructions.split("\n").filter { it.isNotBlank() }
    }

    /**
     * Get tags as a list
     * @return List of individual tags
     */
    fun getTagsList(): List<String> {
        return if (tags.isBlank()) emptyList()
        else tags.split(",").map { it.trim() }.filter { it.isNotBlank() }
    }

    /**
     * Check if recipe has nutrition information
     * @return true if any nutrition field is non-zero
     */
    fun hasNutritionInfo(): Boolean {
        return caloriesKcal > 0 || proteinG > 0 || carbsG > 0 || fatG > 0
    }
}
