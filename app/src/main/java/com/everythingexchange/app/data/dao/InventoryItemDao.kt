package com.everythingexchange.app.data.dao

import androidx.room.*
import com.everythingexchange.app.data.entities.InventoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryItemDao {
    @Query("SELECT * FROM inventory_items WHERE userId = :userId ORDER BY updatedAt DESC")
    fun getItemsByUserId(userId: Long): Flow<List<InventoryItem>>
    
    @Query("SELECT * FROM inventory_items WHERE id = :id")
    suspend fun getItemById(id: Long): InventoryItem?
    
    @Query("SELECT * FROM inventory_items WHERE userId = :userId AND category = :category ORDER BY updatedAt DESC")
    fun getItemsByUserIdAndCategory(userId: Long, category: String): Flow<List<InventoryItem>>
    
    @Insert
    suspend fun insertItem(item: InventoryItem): Long
    
    @Update
    suspend fun updateItem(item: InventoryItem)
    
    @Delete
    suspend fun deleteItem(item: InventoryItem)
    
    @Query("DELETE FROM inventory_items WHERE id = :id")
    suspend fun deleteItemById(id: Long)
    
    @Query("SELECT DISTINCT category FROM inventory_items WHERE userId = :userId ORDER BY category")
    fun getCategoriesByUserId(userId: Long): Flow<List<String>>
}