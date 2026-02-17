package com.example.recipemaster.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipemaster.data.dao.CollectionDao
import com.example.recipemaster.data.dao.RecipeDao
import com.example.recipemaster.data.entity.Collection
import com.example.recipemaster.data.entity.Recipe

/**
 * Room Database class with Collection support
 *
 * @author Heavenlight Mhally
 */
@Database(
    entities = [Recipe::class, Collection::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
    abstract fun collectionDao(): CollectionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "recipe_master_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
