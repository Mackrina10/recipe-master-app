package com.example.recipemaster.data.repository

import com.example.recipemaster.data.dao.RecipeDao
import com.example.recipemaster.data.entity.Recipe
import kotlinx.coroutines.flow.Flow

/**
 * Repository for Recipe data operations
 * Abstracts data source from ViewModel
 *
 * @property recipeDao Recipe DAO
 * @author Heavenlight Mhally
 */
class RecipeRepository(private val recipeDao: RecipeDao) {

    val allRecipes: Flow<List<Recipe>> = recipeDao.getAllRecipes()

    fun getAllRecipesSortedByNameAsc(): Flow<List<Recipe>> =
        recipeDao.getAllRecipesSortedByNameAsc()

    fun getAllRecipesSortedByNameDesc(): Flow<List<Recipe>> =
        recipeDao.getAllRecipesSortedByNameDesc()

    fun getAllRecipesSortedByTimeAsc(): Flow<List<Recipe>> =
        recipeDao.getAllRecipesSortedByTimeAsc()

    fun getAllRecipesSortedByTimeDesc(): Flow<List<Recipe>> =
        recipeDao.getAllRecipesSortedByTimeDesc()

    fun getAllRecipesSortedByRatingDesc(): Flow<List<Recipe>> =
        recipeDao.getAllRecipesSortedByRatingDesc()

    fun getAllRecipesSortedByRatingAsc(): Flow<List<Recipe>> =
        recipeDao.getAllRecipesSortedByRatingAsc()

    fun getAllRecipesSortedByRecent(): Flow<List<Recipe>> =
        recipeDao.getAllRecipesSortedByRecent()

    fun getAllRecipesSortedByOldest(): Flow<List<Recipe>> =
        recipeDao.getAllRecipesSortedByOldest()

    fun getAllRecipesSortedByCookedMost(): Flow<List<Recipe>> =
        recipeDao.getAllRecipesSortedByCookedMost()

    fun getAllRecipesSortedByCookedLeast(): Flow<List<Recipe>> =
        recipeDao.getAllRecipesSortedByCookedLeast()

    fun getRecipeById(id: Int): Flow<Recipe?> = recipeDao.getRecipeById(id)

    fun getRecipesByCategory(category: String): Flow<List<Recipe>> =
        recipeDao.getRecipesByCategory(category)

    fun getFavoriteRecipes(): Flow<List<Recipe>> = recipeDao.getFavoriteRecipes()

    fun searchRecipes(query: String): Flow<List<Recipe>> =
        recipeDao.searchRecipes(query)

    fun filterRecipes(
        categories: List<String>?,
        difficulties: List<String>?,
        minTime: Int,
        maxTime: Int
    ): Flow<List<Recipe>> = recipeDao.filterRecipes(
        categories, difficulties, minTime, maxTime
    )

    suspend fun insert(recipe: Recipe): Long = recipeDao.insert(recipe)

    suspend fun update(recipe: Recipe) = recipeDao.update(recipe)

    suspend fun delete(recipe: Recipe) = recipeDao.delete(recipe)

    suspend fun deleteById(id: Int) = recipeDao.deleteById(id)

    suspend fun toggleFavorite(id: Int, isFavorite: Boolean) =
        recipeDao.toggleFavorite(id, isFavorite)

    suspend fun updateRating(id: Int, rating: Float) =
        recipeDao.updateRating(id, rating)

    suspend fun markAsCooked(id: Int) =
        recipeDao.markAsCooked(id, System.currentTimeMillis())

    suspend fun updateNotes(id: Int, notes: String) =
        recipeDao.updateNotes(id, notes)

    suspend fun getRecipeCount(): Int = recipeDao.getRecipeCount()

    suspend fun getFavoriteCount(): Int = recipeDao.getFavoriteCount()

    suspend fun getAverageRating(): Float = recipeDao.getAverageRating() ?: 0f

    suspend fun getTotalTimesCooked(): Int = recipeDao.getTotalTimesCooked()
}
