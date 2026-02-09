package com.example.recipemaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipemaster.data.repository.RecipeRepository

/**
 * Factory class for creating RecipeViewModel with dependencies
 * Allows passing repository to ViewModel constructor
 *
 * @property repository Recipe repository instance
 * @author Heavenlight Mhally
 */
class RecipeViewModelFactory(
    private val repository: RecipeRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            return RecipeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
