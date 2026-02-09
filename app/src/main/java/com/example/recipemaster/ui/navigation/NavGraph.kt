package com.example.recipemaster.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.recipemaster.viewmodel.RecipeViewModel

/**
 * Main navigation graph for the app
 * Defines all navigation routes and their destinations
 *
 * @param navController Navigation controller
 * @param viewModel Recipe ViewModel
 * @param modifier Modifier for NavHost
 * @author Heavenlight Mhally
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: RecipeViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ) {
        // Home screen (Recipe list)
        composable(Routes.HOME) {
            // Will be implemented in next commits
        }

        // Search screen
        composable(Routes.SEARCH) {
            // Will be implemented
        }

        // Add recipe screen
        composable(Routes.ADD_RECIPE) {
            // Will be implemented
        }

        // Edit recipe screen
        composable(
            route = Routes.EDIT_RECIPE,
            arguments = listOf(
                navArgument("recipeId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
            // Will be implemented
        }

        // Recipe detail screen
        composable(
            route = Routes.RECIPE_DETAIL,
            arguments = listOf(
                navArgument("recipeId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
            // Will be implemented
        }

        // Favorites screen
        composable(Routes.FAVORITES) {
            // Will be implemented
        }

        // Statistics screen
        composable(Routes.STATISTICS) {
            // Will be implemented
        }
    }
}
