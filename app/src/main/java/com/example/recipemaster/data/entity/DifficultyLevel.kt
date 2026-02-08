package com.example.recipemaster.data.entity

/**
 * Enum representing recipe difficulty levels
 * Used to indicate how challenging a recipe is to prepare
 *
 * @property displayName User-friendly name shown in UI
 * @author Heavenlight Mhally
 */
enum class DifficultyLevel(val displayName: String) {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");

    companion object {
        /**
         * Convert display name back to enum
         * @param displayName The display name string
         * @return DifficultyLevel or null if not found
         */
        fun fromDisplayName(displayName: String): DifficultyLevel? {
            return entries.find { it.displayName == displayName }
        }

        /**
         * Get all difficulty level display names
         * @return List of all difficulty names
         */
        fun getAllDisplayNames(): List<String> {
            return entries.map { it.displayName }
        }
    }
}
