package com.example.recipemaster.data.entity

/**
 * Sort options for recipes
 *
 * @author Heavenlight Mhally
 */
enum class SortOption(val displayName: String) {
    NAME_ASC("Name (A-Z)"),
    NAME_DESC("Name (Z-A)"),
    TIME_ASC("Time (Low to High)"),
    TIME_DESC("Time (High to Low)"),
    RATING_DESC("Rating (High to Low)"),
    RATING_ASC("Rating (Low to High)"),
    RECENT("Recently Added"),
    OLDEST("Oldest First"),
    COOKED_MOST("Most Cooked"),
    COOKED_LEAST("Least Cooked");

    companion object {
        fun getAllDisplayNames(): List<String> = entries.map { it.displayName }

        fun fromDisplayName(displayName: String): SortOption? =
            entries.find { it.displayName == displayName }
    }
}
