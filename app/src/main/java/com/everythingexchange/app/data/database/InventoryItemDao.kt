package com.everythingexchange.app.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryItemDao {
    @Query("SELECT * FROM inventory_items WHERE userId = :userId ORDER BY createdAt DESC")
    fun getItemsByUser(userId: Long): Flow<List<InventoryItem>>
    
    @Query("SELECT * FROM inventory_items WHERE id = :id")
    suspend fun getItemById(id: Long): InventoryItem?
    
    @Query("SELECT * FROM inventory_items WHERE name LIKE :searchTerm OR description LIKE :searchTerm")
    fun searchItems(searchTerm: String): Flow<List<InventoryItem>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: InventoryItem): Long
    
    @Update
    suspend fun updateItem(item: InventoryItem)
    
    @Delete
    suspend fun deleteItem(item: InventoryItem)
    
    @Query("SELECT * FROM inventory_items ORDER BY createdAt DESC")
    fun getAllItems(): Flow<List<InventoryItem>>
}