package com.example.recipemaster.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.recipemaster.data.entity.Recipe
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Recipe entity
 * Provides all database operations for recipes
 * Uses Flow for reactive queries that automatically update UI
 *
 * @author Heavenlight Mhally
 */
@Dao
interface RecipeDao {

    // CREATE operations

    /**
     * Insert a new recipe into the database
     * @param recipe Recipe to insert
     * @return Row ID of inserted recipe
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe): Long

    /**
     * Insert multiple recipes at once
     * @param recipes List of recipes to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<Recipe>)

    // READ operations

    /**
     * Get all recipes as a Flow (reactive)
     * Automatically updates when database changes
     * @return Flow of all recipes
     */
    @Query("SELECT * FROM recipes ORDER BY dateCreated DESC")
    fun getAllRecipes(): Flow<List<Recipe>>

    /**
     * Get a single recipe by ID
     * @param recipeId Recipe ID
     * @return Flow of recipe or null
     */
    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    fun getRecipeById(recipeId: Int): Flow<Recipe?>

    /**
     * Get all favorite recipes
     * @return Flow of favorite recipes
     */
    @Query("SELECT * FROM recipes WHERE isFavorite = 1 ORDER BY dateModified DESC")
    fun getFavoriteRecipes(): Flow<List<Recipe>>

    /**
     * Get recipes by category
     * @param category Category name
     * @return Flow of recipes in that category
     */
    @Query("SELECT * FROM recipes WHERE category = :category ORDER BY name ASC")
    fun getRecipesByCategory(category: String): Flow<List<Recipe>>

    /**
     * Search recipes by name or ingredients
     * @param query Search query
     * @return Flow of matching recipes
     */
    @Query("""
        SELECT * FROM recipes 
        WHERE name LIKE '%' || :query || '%' 
        OR ingredients LIKE '%' || :query || '%'
        OR tags LIKE '%' || :query || '%'
        ORDER BY name ASC
    """)
    fun searchRecipes(query: String): Flow<List<Recipe>>

    /**
     * Get recipes with cooking history (timesCooked > 0)
     * Sorted by most recently cooked
     * @return Flow of cooked recipes
     */
    @Query("SELECT * FROM recipes WHERE timesCooked > 0 ORDER BY lastCooked DESC")
    fun getCookedRecipes(): Flow<List<Recipe>>

    // UPDATE operations

    /**
     * Update an existing recipe
     * @param recipe Recipe with updated values
     */
    @Update
    suspend fun update(recipe: Recipe)

    /**
     * Toggle favorite status of a recipe
     * @param recipeId Recipe ID
     * @param isFavorite New favorite status
     */
    @Query("UPDATE recipes SET isFavorite = :isFavorite, dateModified = :timestamp WHERE id = :recipeId")
    suspend fun updateFavoriteStatus(recipeId: Int, isFavorite: Boolean, timestamp: Long = System.currentTimeMillis())

    /**
     * Update rating of a recipe
     * @param recipeId Recipe ID
     * @param rating New rating (0-5)
     */
    @Query("UPDATE recipes SET rating = :rating, dateModified = :timestamp WHERE id = :recipeId")
    suspend fun updateRating(recipeId: Int, rating: Float, timestamp: Long = System.currentTimeMillis())

    /**
     * Increment times cooked counter and update last cooked timestamp
     * @param recipeId Recipe ID
     */
    @Query("UPDATE recipes SET timesCooked = timesCooked + 1, lastCooked = :timestamp, dateModified = :timestamp WHERE id = :recipeId")
    suspend fun markAsCooked(recipeId: Int, timestamp: Long = System.currentTimeMillis())

    /**
     * Update recipe notes
     * @param recipeId Recipe ID
     * @param notes New notes text
     */
    @Query("UPDATE recipes SET notes = :notes, dateModified = :timestamp WHERE id = :recipeId")
    suspend fun updateNotes(recipeId: Int, notes: String, timestamp: Long = System.currentTimeMillis())

    // DELETE operations

    /**
     * Delete a recipe
     * @param recipe Recipe to delete
     */
    @Delete
    suspend fun delete(recipe: Recipe)

    /**
     * Delete recipe by ID
     * @param recipeId Recipe ID
     */
    @Query("DELETE FROM recipes WHERE id = :recipeId")
    suspend fun deleteById(recipeId: Int)

    /**
     * Delete all recipes (use with caution)
     */
    @Query("DELETE FROM recipes")
    suspend fun deleteAll()

    /**
     * Delete multiple recipes by IDs
     * @param recipeIds List of recipe IDs to delete
     */
    @Query("DELETE FROM recipes WHERE id IN (:recipeIds)")
    suspend fun deleteByIds(recipeIds: List<Int>)

    // STATISTICS queries

    /**
     * Get total count of recipes
     * @return Flow of recipe count
     */
    @Query("SELECT COUNT(*) FROM recipes")
    fun getTotalRecipeCount(): Flow<Int>

    /**
     * Get count of favorite recipes
     * @return Flow of favorite count
     */
    @Query("SELECT COUNT(*) FROM recipes WHERE isFavorite = 1")
    fun getFavoriteCount(): Flow<Int>

    /**
     * Get average rating across all recipes
     * @return Flow of average rating
     */
    @Query("SELECT AVG(rating) FROM recipes WHERE rating > 0")
    fun getAverageRating(): Flow<Float?>

    /**
     * Get average cooking time across all recipes
     * @return Flow of average total time in minutes
     */
    @Query("SELECT AVG(totalTimeMin) FROM recipes WHERE totalTimeMin > 0")
    fun getAverageCookingTime(): Flow<Float?>

    /**
     * Get total times all recipes have been cooked
     * @return Flow of total cooked count
     */
    @Query("SELECT SUM(timesCooked) FROM recipes")
    fun getTotalTimesCooked(): Flow<Int?>

    /**
     * Get count of recipes by category
     * @return Flow of map (category name -> count)
     */
    @Query("SELECT category, COUNT(*) as count FROM recipes GROUP BY category")
    fun getRecipeCountByCategory(): Flow<Map<String, Int>>
}
