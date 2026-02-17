package com.example.recipemaster.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Collection entity for organizing recipes
 *
 * @property id Collection ID
 * @property name Collection name
 * @property description Collection description
 * @property recipeIds Comma-separated recipe IDs
 * @property createdAt Creation timestamp
 * @author Heavenlight Mhally
 */
@Entity(tableName = "collections")
data class Collection(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String = "",
    val recipeIds: String = "",
    val createdAt: Long = System.currentTimeMillis()
) {
    fun getRecipeIdsList(): List<Int> {
        return if (recipeIds.isEmpty()) {
            emptyList()
        } else {
            recipeIds.split(",").mapNotNull { it.toIntOrNull() }
        }
    }

    fun addRecipeId(recipeId: Int): Collection {
        val currentIds = getRecipeIdsList().toMutableList()
        if (!currentIds.contains(recipeId)) {
            currentIds.add(recipeId)
        }
        return copy(recipeIds = currentIds.joinToString(","))
    }

    fun removeRecipeId(recipeId: Int): Collection {
        val currentIds = getRecipeIdsList().toMutableList()
        currentIds.remove(recipeId)
        return copy(recipeIds = currentIds.joinToString(","))
    }

    fun getRecipeCount(): Int = getRecipeIdsList().size
}
