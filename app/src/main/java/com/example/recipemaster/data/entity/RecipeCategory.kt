package com.example.recipemaster.data.entity

/**
 * Enum representing different recipe categories
 * Used for categorizing recipes in the app
 *
 * @property displayName User-friendly name shown in UI
 * @author Heavenlight Mhally
 */
enum class RecipeCategory(val displayName: String) {
    BREAKFAST("Breakfast"),
    LUNCH("Lunch"),
    DINNER("Dinner"),
    DESSERT("Dessert"),
    SNACK("Snack"),
    BEVERAGE("Beverage");

    companion object {
        /**
         * Convert display name back to enum
         * @param displayName The display name string
         * @return RecipeCategory or null if not found
         */
        fun fromDisplayName(displayName: String): RecipeCategory? {
            return entries.find { it.displayName == displayName }
        }

        /**
         * Get all category display names
         * @return List of all category names
         */
        fun getAllDisplayNames(): List<String> {
            return entries.map { it.displayName }
        }
    }
}
