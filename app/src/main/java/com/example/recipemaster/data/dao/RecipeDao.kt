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
 * Contains all database operations for recipes
 *
 * @author Heavenlight Mhally
 */
@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipes ORDER BY createdAt DESC")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes ORDER BY name ASC")
    fun getAllRecipesSortedByNameAsc(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes ORDER BY name DESC")
    fun getAllRecipesSortedByNameDesc(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes ORDER BY totalTimeMin ASC")
    fun getAllRecipesSortedByTimeAsc(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes ORDER BY totalTimeMin DESC")
    fun getAllRecipesSortedByTimeDesc(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes ORDER BY rating DESC")
    fun getAllRecipesSortedByRatingDesc(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes ORDER BY rating ASC")
    fun getAllRecipesSortedByRatingAsc(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes ORDER BY createdAt DESC")
    fun getAllRecipesSortedByRecent(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes ORDER BY createdAt ASC")
    fun getAllRecipesSortedByOldest(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes ORDER BY timesCooked DESC")
    fun getAllRecipesSortedByCookedMost(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes ORDER BY timesCooked ASC")
    fun getAllRecipesSortedByCookedLeast(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getRecipeById(id: Int): Flow<Recipe?>

    @Query("SELECT * FROM recipes WHERE category = :category ORDER BY createdAt DESC")
    fun getRecipesByCategory(category: String): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE isFavorite = 1 ORDER BY createdAt DESC")
    fun getFavoriteRecipes(): Flow<List<Recipe>>

    @Query("""
        SELECT * FROM recipes 
        WHERE name LIKE '%' || :query || '%' 
        OR ingredients LIKE '%' || :query || '%'
        OR tags LIKE '%' || :query || '%'
        OR category LIKE '%' || :query || '%'
        ORDER BY createdAt DESC
    """)
    fun searchRecipes(query: String): Flow<List<Recipe>>

    @Query("""
        SELECT * FROM recipes 
        WHERE (:categories IS NULL OR category IN (:categories))
        AND (:difficulties IS NULL OR difficulty IN (:difficulties))
        AND totalTimeMin BETWEEN :minTime AND :maxTime
        ORDER BY createdAt DESC
    """)
    fun filterRecipes(
        categories: List<String>?,
        difficulties: List<String>?,
        minTime: Int,
        maxTime: Int
    ): Flow<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe): Long

    @Update
    suspend fun update(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)

    @Query("DELETE FROM recipes WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("UPDATE recipes SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun toggleFavorite(id: Int, isFavorite: Boolean)

    @Query("UPDATE recipes SET rating = :rating WHERE id = :id")
    suspend fun updateRating(id: Int, rating: Float)

    @Query("UPDATE recipes SET timesCooked = timesCooked + 1, lastCooked = :timestamp WHERE id = :id")
    suspend fun markAsCooked(id: Int, timestamp: Long)

    @Query("UPDATE recipes SET notes = :notes WHERE id = :id")
    suspend fun updateNotes(id: Int, notes: String)

    @Query("SELECT COUNT(*) FROM recipes")
    suspend fun getRecipeCount(): Int

    @Query("SELECT COUNT(*) FROM recipes WHERE isFavorite = 1")
    suspend fun getFavoriteCount(): Int

    @Query("SELECT AVG(rating) FROM recipes WHERE rating > 0")
    suspend fun getAverageRating(): Float?

    @Query("SELECT SUM(timesCooked) FROM recipes")
    suspend fun getTotalTimesCooked(): Int
}
