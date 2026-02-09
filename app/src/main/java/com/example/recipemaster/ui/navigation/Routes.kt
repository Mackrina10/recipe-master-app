package com.example.recipemaster.ui.navigation

/**
 * Navigation route constants for the app
 * Defines all navigation destinations
 *
 * @author Heavenlight Mhally
 */
object Routes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val HOME = "home"
    const val SEARCH = "search"
    const val ADD_RECIPE = "add_recipe"
    const val EDIT_RECIPE = "edit_recipe/{recipeId}"
    const val RECIPE_DETAIL = "recipe_detail/{recipeId}"
    const val FAVORITES = "favorites"
    const val STATISTICS = "statistics"
    const val HISTORY = "history"
    const val SHOPPING_LIST = "shopping_list"
    const val SETTINGS = "settings"
    const val BACKUP = "backup"
    const val PRIVACY = "privacy"
    const val ABOUT = "about"
    const val CHANGELOG = "changelog"
    const val FEEDBACK = "feedback"
    const val GESTURE_GUIDE = "gesture_guide"
    const val COLLECTIONS = "collections"
    const val COLLECTION_DETAIL = "collection_detail/{collectionId}"
    const val MEAL_PLANNER = "meal_planner"
    const val COOKING_MODE = "cooking_mode/{recipeId}"

    /**
     * Create recipe detail route with ID
     * @param recipeId Recipe ID
     * @return Formatted route string
     */
    fun recipeDetail(recipeId: Int) = "recipe_detail/$recipeId"

    /**
     * Create edit recipe route with ID
     * @param recipeId Recipe ID
     * @return Formatted route string
     */
    fun editRecipe(recipeId: Int) = "edit_recipe/$recipeId"

    /**
     * Create cooking mode route with ID
     * @param recipeId Recipe ID
     * @return Formatted route string
     */
    fun cookingMode(recipeId: Int) = "cooking_mode/$recipeId"

    /**
     * Create collection detail route with ID
     * @param collectionId Collection ID
     * @return Formatted route string
     */
    fun collectionDetail(collectionId: Long) = "collection_detail/$collectionId"
}
