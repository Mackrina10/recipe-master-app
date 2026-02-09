package com.example.recipemaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipemaster.data.entity.Recipe
import com.example.recipemaster.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for Recipe operations
 * Manages UI state and business logic for recipes
 * Uses StateFlow for reactive UI updates
 *
 * @property repository Recipe repository for data access
 * @author Heavenlight Mhally
 */
class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

    // All recipes from database
    val allRecipes: StateFlow<List<Recipe>> = repository.allRecipes
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Filtered/searched recipes
    private val _filteredRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val filteredRecipes: StateFlow<List<Recipe>> = _filteredRecipes.asStateFlow()

    // Selected category filter
    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // Search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Error state
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        // Initially show all recipes
        viewModelScope.launch {
            allRecipes.collect { recipes ->
                _filteredRecipes.value = recipes
            }
        }
    }

    /**
     * Insert a new recipe
     * @param recipe Recipe to add
     */
    fun addRecipe(recipe: Recipe) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.insert(recipe)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to add recipe: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Update an existing recipe
     * @param recipe Recipe with updated data
     */
    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.update(recipe)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to update recipe: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Delete a recipe
     * @param recipe Recipe to delete
     */
    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            try {
                repository.delete(recipe)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to delete recipe: ${e.message}"
            }
        }
    }

    /**
     * Delete recipe by ID
     * @param id Recipe ID
     */
    fun deleteRecipeById(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteById(id)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to delete recipe: ${e.message}"
            }
        }
    }

    /**
     * Toggle favorite status of a recipe
     * @param recipeId Recipe ID
     * @param isFavorite New favorite status
     */
    fun toggleFavorite(recipeId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            try {
                repository.toggleFavorite(recipeId, isFavorite)
            } catch (e: Exception) {
                _error.value = "Failed to update favorite: ${e.message}"
            }
        }
    }

    /**
     * Update recipe rating
     * @param recipeId Recipe ID
     * @param rating New rating value
     */
    fun updateRating(recipeId: Int, rating: Float) {
        viewModelScope.launch {
            try {
                repository.updateRating(recipeId, rating)
            } catch (e: Exception) {
                _error.value = "Failed to update rating: ${e.message}"
            }
        }
    }

    /**
     * Mark recipe as cooked
     * @param recipeId Recipe ID
     */
    fun markAsCooked(recipeId: Int) {
        viewModelScope.launch {
            try {
                repository.markAsCooked(recipeId)
            } catch (e: Exception) {
                _error.value = "Failed to mark as cooked: ${e.message}"
            }
        }
    }

    /**
     * Update recipe notes
     * @param recipeId Recipe ID
     * @param notes New notes text
     */
    fun updateNotes(recipeId: Int, notes: String) {
        viewModelScope.launch {
            try {
                repository.updateNotes(recipeId, notes)
            } catch (e: Exception) {
                _error.value = "Failed to update notes: ${e.message}"
            }
        }
    }

    /**
     * Filter recipes by category
     * @param category Category name or "All"
     */
    fun filterByCategory(category: String) {
        _selectedCategory.value = category
        viewModelScope.launch {
            if (category == "All") {
                allRecipes.collect { recipes ->
                    _filteredRecipes.value = recipes
                }
            } else {
                repository.getRecipesByCategory(category).collect { recipes ->
                    _filteredRecipes.value = recipes
                }
            }
        }
    }

    /**
     * Search recipes by query
     * @param query Search string
     */
    fun searchRecipes(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            if (query.isBlank()) {
                allRecipes.collect { recipes ->
                    _filteredRecipes.value = recipes
                }
            } else {
                repository.searchRecipes(query).collect { recipes ->
                    _filteredRecipes.value = recipes
                }
            }
        }
    }

    /**
     * Clear error message
     */
    fun clearError() {
        _error.value = null
    }
}
