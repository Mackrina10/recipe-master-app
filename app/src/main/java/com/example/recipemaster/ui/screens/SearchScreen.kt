package com.example.recipemaster.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recipemaster.ui.components.EmptyState
import com.example.recipemaster.ui.components.FilterBottomSheet
import com.example.recipemaster.ui.components.RecipeCard
import com.example.recipemaster.ui.components.SortMenu
import com.example.recipemaster.ui.navigation.Routes
import com.example.recipemaster.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

/**
 * Advanced search screen with filters and sorting
 *
 * @param navController Navigation controller
 * @param viewModel Recipe view model
 * @author Heavenlight Mhally
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: RecipeViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    val searchResults by viewModel.filteredRecipes.collectAsState()
    val currentSort by viewModel.currentSortOption.collectAsState()

    var showFilterSheet by remember { mutableStateOf(false) }
    val filterSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Search Recipes",
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
                    IconButton(onClick = { showFilterSheet = true }) {
                        Icon(
                            imageVector = Icons.Filled.FilterList,
                            contentDescription = "Filter"
                        )
                    }
                    SortMenu(
                        currentSort = currentSort,
                        onSortSelected = { sortOption ->
                            viewModel.applySorting(sortOption)
                        }
                    )
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
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { query ->
                    searchQuery = query
                    viewModel.searchRecipes(query)
                },
                onSearch = {
                    isSearchActive = false
                    viewModel.searchRecipes(searchQuery)
                },
                active = isSearchActive,
                onActiveChange = { isSearchActive = it },
                placeholder = { Text("Search by name, ingredient...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = {
                            searchQuery = ""
                            viewModel.searchRecipes("")
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Clear"
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Empty suggestions for now
            }

            if (searchQuery.isEmpty()) {
                EmptyState(
                    icon = Icons.Filled.Search,
                    title = "Search for recipes",
                    message = "Enter recipe name, ingredient, or category to search"
                )
            } else if (searchResults.isEmpty()) {
                EmptyState(
                    icon = Icons.Filled.Search,
                    title = "No results found",
                    message = "Try searching with different keywords"
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = searchResults,
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

        if (showFilterSheet) {
            FilterBottomSheet(
                sheetState = filterSheetState,
                onDismiss = {
                    scope.launch {
                        filterSheetState.hide()
                        showFilterSheet = false
                    }
                },
                onApplyFilters = { categories, difficulties, timeRange ->
                    viewModel.applyFilters(categories, difficulties, timeRange)
                }
            )
        }
    }
}
