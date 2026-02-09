package com.example.recipemaster.data.repository

import com.example.recipemaster.data.dao.RecipeDao
import com.example.recipemaster.data.entity.Recipe
import kotlinx.coroutines.flow.Flow

/**
 * Repository class for Recipe operations
 * Provides a clean API for accessing recipe data
 * Acts as single source of truth between ViewModel and Database
 *
 * @property recipeDao Data Access Object for recipes
 * @author Heavenlight Mhally
 */
class RecipeRepository(private val recipeDao: RecipeDao) {

    // All recipes as Flow (automatically updates UI)
    val allRecipes: Flow<List<Recipe>> = recipeDao.getAllRecipes()
    val favoriteRecipes: Flow<List<Recipe>> = recipeDao.getFavoriteRecipes()
    val cookedRecipes: Flow<List<Recipe>> = recipeDao.getCookedRecipes()

    // Statistics
    val totalRecipeCount: Flow<Int> = recipeDao.getTotalRecipeCount()
    val favoriteCount: Flow<Int> = recipeDao.getFavoriteCount()
    val averageRating: Flow<Float?> = recipeDao.getAverageRating()
    val averageCookingTime: Flow<Float?> = recipeDao.getAverageCookingTime()
    val totalTimesCooked: Flow<Int?> = recipeDao.getTotalTimesCooked()

    /**
     * Get a single recipe by ID
     * @param id Recipe ID
     * @return Flow of Recipe or null
     */
    fun getRecipeById(id: Int): Flow<Recipe?> {
        return recipeDao.getRecipeById(id)
    }

    /**
     * Get recipes by category
     * @param category Category name
     * @return Flow of recipes
     */
    fun getRecipesByCategory(category: String): Flow<List<Recipe>> {
        return recipeDao.getRecipesByCategory(category)
    }

    /**
     * Search recipes by query
     * @param query Search string
     * @return Flow of matching recipes
     */
    fun searchRecipes(query: String): Flow<List<Recipe>> {
        return recipeDao.searchRecipes(query)
    }

    /**
     * Insert a new recipe
     * @param recipe Recipe to insert
     * @return Row ID of inserted recipe
     */
    suspend fun insert(recipe: Recipe): Long {
        return recipeDao.insert(recipe)
    }

    /**
     * Insert multiple recipes
     * @param recipes List of recipes
     */
    suspend fun insertAll(recipes: List<Recipe>) {
        recipeDao.insertAll(recipes)
    }

    /**
     * Update an existing recipe
     * @param recipe Recipe with updated data
     */
    suspend fun update(recipe: Recipe) {
        recipeDao.update(recipe.copy(dateModified = System.currentTimeMillis()))
    }

    /**
     * Delete a recipe
     * @param recipe Recipe to delete
     */
    suspend fun delete(recipe: Recipe) {
        recipeDao.delete(recipe)
    }

    /**
     * Delete recipe by ID
     * @param id Recipe ID
     */
    suspend fun deleteById(id: Int) {
        recipeDao.deleteById(id)
    }

    /**
     * Delete multiple recipes
     * @param ids List of recipe IDs
     */
    suspend fun deleteByIds(ids: List<Int>) {
        recipeDao.deleteByIds(ids)
    }

    /**
     * Toggle favorite status
     * @param id Recipe ID
     * @param isFavorite New favorite status
     */
    suspend fun toggleFavorite(id: Int, isFavorite: Boolean) {
        recipeDao.updateFavoriteStatus(id, isFavorite)
    }

    /**
     * Update recipe rating
     * @param id Recipe ID
     * @param rating New rating (0-5)
     */
    suspend fun updateRating(id: Int, rating: Float) {
        recipeDao.updateRating(id, rating)
    }

    /**
     * Mark recipe as cooked
     * Increments counter and updates timestamp
     * @param id Recipe ID
     */
    suspend fun markAsCooked(id: Int) {
        recipeDao.markAsCooked(id)
    }

    /**
     * Update recipe notes
     * @param id Recipe ID
     * @param notes New notes text
     */
    suspend fun updateNotes(id: Int, notes: String) {
        recipeDao.updateNotes(id, notes)
    }

    /**
     * Delete all recipes
     */
    suspend fun deleteAll() {
        recipeDao.deleteAll()
    }
}
