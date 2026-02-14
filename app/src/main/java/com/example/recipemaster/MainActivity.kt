package com.example.recipemaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipemaster.data.database.AppDatabase
import com.example.recipemaster.data.repository.RecipeRepository
import com.example.recipemaster.ui.components.BottomNavigationBar
import com.example.recipemaster.ui.navigation.Routes
import com.example.recipemaster.ui.screens.AddRecipeScreen
import com.example.recipemaster.ui.screens.RecipeDetailScreen
import com.example.recipemaster.ui.screens.RecipeListScreen
import com.example.recipemaster.ui.theme.RecipeMasterTheme
import com.example.recipemaster.viewmodel.RecipeViewModel
import com.example.recipemaster.viewmodel.RecipeViewModelFactory

/**
 * Main Activity
 * Entry point of Recipe Master app
 *
 * @author Heavenlight Mhally
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = RecipeRepository(database.recipeDao())

        setContent {
            RecipeMasterTheme {
                RecipeMasterApp(repository)
            }
        }
    }
}

@Composable
fun RecipeMasterApp(repository: RecipeRepository) {
    val navController = rememberNavController()
    val viewModel: RecipeViewModel = viewModel(
        factory = RecipeViewModelFactory(repository)
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Routes.HOME) {
                RecipeListScreen(navController, viewModel)
            }

            composable(Routes.SEARCH) {
                RecipeListScreen(navController, viewModel)
            }

            composable(Routes.ADD_RECIPE) {
                AddRecipeScreen(navController, viewModel)
            }

            composable(Routes.FAVORITES) {
                RecipeListScreen(navController, viewModel)
            }

            composable(Routes.STATISTICS) {
                RecipeListScreen(navController, viewModel)
            }

            composable(
                route = Routes.RECIPE_DETAIL,
                arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
            ) { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
                RecipeDetailScreen(recipeId, navController, viewModel)
            }

            composable(
                route = Routes.EDIT_RECIPE,
                arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
            ) { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getInt("recipeId")
                AddRecipeScreen(navController, viewModel, recipeId)
            }
        }
    }
}
