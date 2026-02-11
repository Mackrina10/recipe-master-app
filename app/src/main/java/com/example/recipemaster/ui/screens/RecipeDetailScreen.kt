package com.example.recipemaster.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recipemaster.data.entity.Recipe
import com.example.recipemaster.ui.components.RatingBar
import com.example.recipemaster.ui.components.TagChips
import com.example.recipemaster.ui.navigation.Routes
import com.example.recipemaster.viewmodel.RecipeViewModel
import kotlinx.coroutines.flow.filterNotNull
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Recipe detail screen with mark as cooked functionality
 *
 * @param recipeId Recipe ID
 * @param navController Navigation controller
 * @param viewModel Recipe view model
 * @author Heavenlight Mhally
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    navController: NavController,
    viewModel: RecipeViewModel
) {
    var recipe by remember { mutableStateOf<Recipe?>(null) }

    LaunchedEffect(recipeId) {
        viewModel.repository.getRecipeById(recipeId)
            .filterNotNull()
            .collect { loadedRecipe ->
                recipe = loadedRecipe
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recipe Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    recipe?.let { currentRecipe ->
                        IconButton(onClick = {
                            viewModel.toggleFavorite(currentRecipe.id, !currentRecipe.isFavorite)
                        }) {
                            Icon(
                                imageVector = if (currentRecipe.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (currentRecipe.isFavorite) Color(0xFFE63946) else MaterialTheme.colorScheme.onSurface
                            )
                        }
                        IconButton(onClick = {
                            navController.navigate(Routes.editRecipe(currentRecipe.id))
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Edit"
                            )
                        }
                        IconButton(onClick = {
                            viewModel.deleteRecipe(currentRecipe)
                            navController.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete"
                            )
                        }
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
        recipe?.let { currentRecipe ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Restaurant,
                        contentDescription = "Recipe image",
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = currentRecipe.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = currentRecipe.category,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        )

                        if (currentRecipe.difficulty.isNotEmpty()) {
                            Text(
                                text = currentRecipe.difficulty,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.secondaryContainer,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.AccessTime,
                                contentDescription = "Time",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Column {
                                Text(
                                    text = "Total Time",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "${currentRecipe.totalTimeMin} min",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Group,
                                contentDescription = "Servings",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Column {
                                Text(
                                    text = "Servings",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "${currentRecipe.servings}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    Column {
                        Text(
                            text = "Rate this recipe",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            RatingBar(
                                rating = currentRecipe.rating,
                                onRatingChanged = { newRating ->
                                    viewModel.updateRating(currentRecipe.id, newRating)
                                },
                                starSize = 32.dp
                            )
                            if (currentRecipe.rating > 0) {
                                Text(
                                    text = String.format("%.1f", currentRecipe.rating),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    if (currentRecipe.timesCooked > 0) {
                        Column {
                            Text(
                                text = "Cooked ${currentRecipe.timesCooked} ${if (currentRecipe.timesCooked == 1) "time" else "times"}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            currentRecipe.lastCooked?.let { timestamp ->
                                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                                Text(
                                    text = "Last cooked: ${dateFormat.format(Date(timestamp))}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    Button(
                        onClick = { viewModel.markAsCooked(currentRecipe.id) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text("Mark as Cooked")
                    }

                    if (currentRecipe.getTagsList().isNotEmpty()) {
                        TagChips(tags = currentRecipe.getTagsList())
                    }

                    if (currentRecipe.description.isNotEmpty()) {
                        HorizontalDivider()
                        Column {
                            Text(
                                text = "Description",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = currentRecipe.description,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    if (currentRecipe.ingredients.isNotEmpty()) {
                        HorizontalDivider()
                        Column {
                            Text(
                                text = "Ingredients",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            currentRecipe.getIngredientsList().forEach { ingredient ->
                                Text(
                                    text = "• $ingredient",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                        }
                    }

                    if (currentRecipe.instructions.isNotEmpty()) {
                        HorizontalDivider()
                        Column {
                            Text(
                                text = "Instructions",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            currentRecipe.getInstructionsList().forEachIndexed { index, instruction ->
                                Text(
                                    text = "${index + 1}. $instruction",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
