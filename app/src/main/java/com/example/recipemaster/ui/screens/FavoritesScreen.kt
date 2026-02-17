package com.example.recipemaster.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recipemaster.ui.components.EmptyState
import com.example.recipemaster.ui.components.RecipeCard
import com.example.recipemaster.ui.navigation.Routes
import com.example.recipemaster.viewmodel.RecipeViewModel
import kotlinx.coroutines.flow.map

/**
 * Favorites screen with grid/list view toggle
 *
 * @param navController Navigation controller
 * @param viewModel Recipe view model
 * @author Heavenlight Mhally
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: RecipeViewModel
) {
    var isGridView by remember { mutableStateOf(false) }

    val favoriteRecipes by viewModel.allRecipes
        .map { recipes -> recipes.filter { it.isFavorite } }
        .collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Favorites",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { isGridView = !isGridView }) {
                        Icon(
                            imageVector = if (isGridView) Icons.Filled.ViewList else Icons.Filled.GridView,
                            contentDescription = if (isGridView) "List View" else "Grid View"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (favoriteRecipes.isEmpty()) {
                EmptyState(
                    icon = Icons.Filled.Favorite,
                    title = "No favorites yet",
                    message = "Tap the heart icon on recipes to add them to favorites"
                )
            } else {
                if (isGridView) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = favoriteRecipes,
                            key = { recipe -> recipe.id }
                        ) { recipe ->
                            RecipeCard(
                                recipe = recipe,
                                onClick = {
                                    navController.navigate(Routes.recipeDetail(recipe.id))
                                },
                                onFavoriteClick = {
                                    viewModel.toggleFavorite(recipe.id, !recipe.isFavorite)
                                }
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = favoriteRecipes,
                            key = { recipe -> recipe.id }
                        ) { recipe ->
                            RecipeCard(
                                recipe = recipe,
                                onClick = {
                                    navController.navigate(Routes.recipeDetail(recipe.id))
                                },
                                onFavoriteClick = {
                                    viewModel.toggleFavorite(recipe.id, !recipe.isFavorite)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
