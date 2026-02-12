package com.example.recipemaster.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recipemaster.data.entity.Recipe
import com.example.recipemaster.viewmodel.RecipeViewModel

/**
 * Add recipe screen with form validation
 * Allows users to create new recipes
 *
 * @param navController Navigation controller
 * @param viewModel Recipe view model
 * @param recipeId Optional recipe ID for editing
 * @author Heavenlight Mhally
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    navController: NavController,
    viewModel: RecipeViewModel,
    recipeId: Int? = null
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var cuisine by remember { mutableStateOf("") }
    var difficulty by remember { mutableStateOf("") }
    var prepTime by remember { mutableIntStateOf(0) }
    var cookTime by remember { mutableIntStateOf(0) }
    var servings by remember { mutableIntStateOf(1) }
    var ingredients by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf<String?>(null) }
    var categoryError by remember { mutableStateOf<String?>(null) }

    val isEditing = recipeId != null

    fun validateForm(): Boolean {
        var isValid = true

        if (name.length < 3) {
            nameError = "Recipe name must be at least 3 characters"
            isValid = false
        } else {
            nameError = null
        }

        if (category.isEmpty()) {
            categoryError = "Please select a category"
            isValid = false
        } else {
            categoryError = null
        }

        return isValid
    }

    fun saveRecipe() {
        if (validateForm()) {
            val totalTime = prepTime + cookTime
            val recipe = Recipe(
                id = recipeId ?: 0,
                name = name,
                description = description,
                category = category,
                cuisine = cuisine,
                difficulty = difficulty,
                prepTimeMin = prepTime,
                cookTimeMin = cookTime,
                totalTimeMin = totalTime,
                servings = servings,
                ingredients = ingredients,
                instructions = instructions,
                tags = tags
            )

            if (isEditing) {
                viewModel.updateRecipe(recipe)
            } else {
                viewModel.addRecipe(recipe)
            }
            navController.navigateUp()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isEditing) "Edit Recipe" else "Add Recipe",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { saveRecipe() }) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Save"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Basic Information",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            // Form fields will be added in next commits
            Text(
                text = "Form fields will be implemented in next commits",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
