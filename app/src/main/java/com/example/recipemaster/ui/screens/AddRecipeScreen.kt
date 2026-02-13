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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recipemaster.data.entity.Recipe
import com.example.recipemaster.ui.components.CategorySelector
import com.example.recipemaster.ui.components.DifficultySelector
import com.example.recipemaster.ui.components.IngredientInput
import com.example.recipemaster.ui.components.StepBuilder
import com.example.recipemaster.ui.components.TimeServingsInput
import com.example.recipemaster.ui.components.ValidatedTextField
import com.example.recipemaster.viewmodel.RecipeViewModel
import kotlinx.coroutines.flow.filterNotNull

/**
 * Add/Edit recipe screen with complete form
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

    LaunchedEffect(recipeId) {
        recipeId?.let { id ->
            viewModel.repository.getRecipeById(id)
                .filterNotNull()
                .collect { recipe ->
                    name = recipe.name
                    description = recipe.description
                    category = recipe.category
                    cuisine = recipe.cuisine
                    difficulty = recipe.difficulty
                    prepTime = recipe.prepTimeMin
                    cookTime = recipe.cookTimeMin
                    servings = recipe.servings
                    ingredients = recipe.ingredients
                    instructions = recipe.instructions
                    tags = recipe.tags
                }
        }
    }

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

            ValidatedTextField(
                value = name,
                onValueChange = { name = it },
                label = "Recipe Name *",
                error = nameError
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = "Description",
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            Text(
                text = "Category *",
                style = MaterialTheme.typography.labelLarge
            )
            CategorySelector(
                selectedCategory = category,
                onCategorySelected = { category = it },
                error = categoryError
            )

            OutlinedTextField(
                value = cuisine,
                onValueChange = { cuisine = it },
                label = "Cuisine (Optional)",
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Difficulty",
                style = MaterialTheme.typography.labelLarge
            )
            DifficultySelector(
                selectedDifficulty = difficulty,
                onDifficultySelected = { difficulty = it }
            )

            HorizontalDivider()

            Text(
                text = "Time & Servings",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            TimeServingsInput(
                prepTime = prepTime,
                cookTime = cookTime,
                servings = servings,
                onPrepTimeChange = { prepTime = it },
                onCookTimeChange = { cookTime = it },
                onServingsChange = { servings = it }
            )

            HorizontalDivider()

            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            IngredientInput(
                ingredients = ingredients,
                onIngredientsChange = { ingredients = it }
            )

            HorizontalDivider()

            Text(
                text = "Instructions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            StepBuilder(
                instructions = instructions,
                onInstructionsChange = { instructions = it }
            )

            HorizontalDivider()

            Text(
                text = "Tags",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            OutlinedTextField(
                value = tags,
                onValueChange = { tags = it },
                label = "Tags (comma-separated)",
                placeholder = { Text("pasta, italian, quick") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { saveRecipe() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isEditing) "Update Recipe" else "Save Recipe")
            }
        }
    }
}
