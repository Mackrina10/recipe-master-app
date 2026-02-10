package com.example.recipemaster.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.recipemaster.ui.navigation.Routes

/**
 * Bottom navigation bar item data class
 *
 * @property route Navigation route
 * @property icon Icon to display
 * @property label Text label
 */
data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

/**
 * Bottom navigation bar component
 * Shows 5 main tabs: Home, Search, Add, Favorites, Statistics
 *
 * @param navController Navigation controller for routing
 * @author Heavenlight Mhally
 */
@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem(Routes.HOME, Icons.Filled.Home, "Home"),
        BottomNavItem(Routes.SEARCH, Icons.Filled.Search, "Search"),
        BottomNavItem(Routes.ADD_RECIPE, Icons.Filled.Add, "Add"),
        BottomNavItem(Routes.FAVORITES, Icons.Filled.Favorite, "Favorites"),
        BottomNavItem(Routes.STATISTICS, Icons.Filled.BarChart, "Stats")
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    // Navigate only if not already on the route
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            // Pop up to start destination to avoid building up back stack
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            // Avoid multiple copies of same destination
                            launchSingleTop = true
                            // Restore state when reselecting previously selected item
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
