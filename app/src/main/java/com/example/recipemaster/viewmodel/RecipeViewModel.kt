package com.example.recipemaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipemaster.data.entity.Recipe
import com.example.recipemaster.data.entity.SortOption
import com.example.recipemaster.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for Recipe operations with sorting
 *
 * @property repository Recipe repository
 * @author Heavenlight Mhally
 */
class RecipeViewModel(val repository: RecipeRepository) : ViewModel() {

    private val _currentSortOption = MutableStateFlow(SortOption.RECENT)
    val currentSortOption: StateFlow<SortOption> = _currentSortOption.asStateFlow()

    val allRecipes: StateFlow<List<Recipe>> = repository.allRecipes
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _filteredRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val filteredRecipes: StateFlow<List<Recipe>> = _filteredRecipes.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        viewModelScope.launch {
            allRecipes.collect { recipes ->
                _filteredRecipes.value = recipes
            }
        }
    }

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

    fun toggleFavorite(recipeId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            try {
                repository.toggleFavorite(recipeId, isFavorite)
            } catch (e: Exception) {
                _error.value = "Failed to update favorite: ${e.message}"
            }
        }
    }

    fun updateRating(recipeId: Int, rating: Float) {
        viewModelScope.launch {
            try {
                repository.updateRating(recipeId, rating)
            } catch (e: Exception) {
                _error.value = "Failed to update rating: ${e.message}"
            }
        }
    }

    fun markAsCooked(recipeId: Int) {
        viewModelScope.launch {
            try {
                repository.markAsCooked(recipeId)
            } catch (e: Exception) {
                _error.value = "Failed to mark as cooked: ${e.message}"
            }
        }
    }

    fun updateNotes(recipeId: Int, notes: String) {
        viewModelScope.launch {
            try {
                repository.updateNotes(recipeId, notes)
            } catch (e: Exception) {
                _error.value = "Failed to update notes: ${e.message}"
            }
        }
    }

    fun filterByCategory(category: String) {
        _selectedCategory.value = category
        viewModelScope.launch {
            if (category == "All") {
                applySorting(_currentSortOption.value)
            } else {
                repository.getRecipesByCategory(category).collect { recipes ->
                    _filteredRecipes.value = recipes
                }
            }
        }
    }

    fun searchRecipes(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            if (query.isBlank()) {
                applySorting(_currentSortOption.value)
            } else {
                repository.searchRecipes(query).collect { recipes ->
                    _filteredRecipes.value = recipes
                }
            }
        }
    }

    fun applySorting(sortOption: SortOption) {
        _currentSortOption.value = sortOption
        viewModelScope.launch {
            val flow = when (sortOption) {
                SortOption.NAME_ASC -> repository.getAllRecipesSortedByNameAsc()
                SortOption.NAME_DESC -> repository.getAllRecipesSortedByNameDesc()
                SortOption.TIME_ASC -> repository.getAllRecipesSortedByTimeAsc()
                SortOption.TIME_DESC -> repository.getAllRecipesSortedByTimeDesc()
                SortOption.RATING_DESC -> repository.getAllRecipesSortedByRatingDesc()
                SortOption.RATING_ASC -> repository.getAllRecipesSortedByRatingAsc()
                SortOption.RECENT -> repository.getAllRecipesSortedByRecent()
                SortOption.OLDEST -> repository.getAllRecipesSortedByOldest()
                SortOption.COOKED_MOST -> repository.getAllRecipesSortedByCookedMost()
                SortOption.COOKED_LEAST -> repository.getAllRecipesSortedByCookedLeast()
            }

            flow.collect { recipes ->
                _filteredRecipes.value = recipes
            }
        }
    }

    fun applyFilters(
        categories: List<String>,
        difficulties: List<String>,
        timeRange: ClosedFloatingPointRange<Float>
    ) {
        viewModelScope.launch {
            repository.filterRecipes(
                categories = if (categories.isEmpty()) null else categories,
                difficulties = if (difficulties.isEmpty()) null else difficulties,
                minTime = timeRange.start.toInt(),
                maxTime = timeRange.endInclusive.toInt()
            ).collect { recipes ->
                _filteredRecipes.value = recipes
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
