package com.example.recipemaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipemaster.data.repository.CollectionRepository

/**
 * Factory for CollectionViewModel
 *
 * @author Heavenlight Mhally
 */
class CollectionViewModelFactory(
    private val repository: CollectionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CollectionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CollectionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
