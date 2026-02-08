package com.example.recipemaster.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipemaster.data.dao.RecipeDao
import com.example.recipemaster.data.entity.Recipe

/**
 * Room Database for Recipe Master application
 * Singleton pattern ensures only one instance exists
 *
 * Version 1: Initial database with Recipe table
 *
 * @author Heavenlight Mhally
 */
@Database(
    entities = [Recipe::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Get Recipe Data Access Object
     * @return RecipeDao instance
     */
    abstract fun recipeDao(): RecipeDao

    companion object {
        // Singleton instance
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Get database instance (singleton)
         * Thread-safe implementation using double-checked locking
         *
         * @param context Application context
         * @return AppDatabase instance
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "recipe_master_database"
                )
                    .fallbackToDestructiveMigration() // For development - will drop and recreate on schema changes
                    .build()

                INSTANCE = instance
                instance
            }
        }

        /**
         * Clear database instance (useful for testing)
         */
        fun clearInstance() {
            INSTANCE = null
        }
    }
}
