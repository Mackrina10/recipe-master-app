package com.example.recipemaster.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.recipemaster.data.entity.Collection
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Collection entity
 *
 * @author Heavenlight Mhally
 */
@Dao
interface CollectionDao {

    @Query("SELECT * FROM collections ORDER BY createdAt DESC")
    fun getAllCollections(): Flow<List<Collection>>

    @Query("SELECT * FROM collections WHERE id = :id")
    fun getCollectionById(id: Int): Flow<Collection?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collection: Collection): Long

    @Update
    suspend fun update(collection: Collection)

    @Delete
    suspend fun delete(collection: Collection)

    @Query("DELETE FROM collections WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT COUNT(*) FROM collections")
    suspend fun getCollectionCount(): Int
}
