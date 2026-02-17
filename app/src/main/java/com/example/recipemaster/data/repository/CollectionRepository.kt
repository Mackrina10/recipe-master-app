package com.example.recipemaster.data.repository

import com.example.recipemaster.data.dao.CollectionDao
import com.example.recipemaster.data.entity.Collection
import kotlinx.coroutines.flow.Flow

/**
 * Repository for Collection data operations
 *
 * @property collectionDao Collection DAO
 * @author Heavenlight Mhally
 */
class CollectionRepository(private val collectionDao: CollectionDao) {

    val allCollections: Flow<List<Collection>> = collectionDao.getAllCollections()

    fun getCollectionById(id: Int): Flow<Collection?> =
        collectionDao.getCollectionById(id)

    suspend fun insert(collection: Collection): Long =
        collectionDao.insert(collection)

    suspend fun update(collection: Collection) =
        collectionDao.update(collection)

    suspend fun delete(collection: Collection) =
        collectionDao.delete(collection)

    suspend fun deleteById(id: Int) =
        collectionDao.deleteById(id)

    suspend fun getCollectionCount(): Int =
        collectionDao.getCollectionCount()
}
