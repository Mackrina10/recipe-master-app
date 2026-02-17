package com.example.recipemaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipemaster.data.entity.Collection
import com.example.recipemaster.data.repository.CollectionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for Collection operations
 *
 * @property repository Collection repository
 * @author Heavenlight Mhally
 */
class CollectionViewModel(val repository: CollectionRepository) : ViewModel() {

    val allCollections: StateFlow<List<Collection>> = repository.allCollections
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun createCollection(name: String, description: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val collection = Collection(
                    name = name,
                    description = description
                )
                repository.insert(collection)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to create collection: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateCollection(collection: Collection) {
        viewModelScope.launch {
            try {
                repository.update(collection)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to update collection: ${e.message}"
            }
        }
    }

    fun deleteCollection(collection: Collection) {
        viewModelScope.launch {
            try {
                repository.delete(collection)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to delete collection: ${e.message}"
            }
        }
    }

    fun addRecipeToCollection(collectionId: Int, recipeId: Int) {
        viewModelScope.launch {
            try {
                repository.getCollectionById(collectionId).collect { collection ->
                    collection?.let {
                        val updatedCollection = it.addRecipeId(recipeId)
                        repository.update(updatedCollection)
                    }
                }
            } catch (e: Exception) {
                _error.value = "Failed to add recipe: ${e.message}"
            }
        }
    }

    fun removeRecipeFromCollection(collectionId: Int, recipeId: Int) {
        viewModelScope.launch {
            try {
                repository.getCollectionById(collectionId).collect { collection ->
                    collection?.let {
                        val updatedCollection = it.removeRecipeId(recipeId)
                        repository.update(updatedCollection)
                    }
                }
            } catch (e: Exception) {
                _error.value = "Failed to remove recipe: ${e.message}"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
