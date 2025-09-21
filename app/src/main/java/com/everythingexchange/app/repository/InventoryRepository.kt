package com.everythingexchange.app.repository

import com.everythingexchange.app.data.dao.InventoryItemDao
import com.everythingexchange.app.data.entities.InventoryItem
import kotlinx.coroutines.flow.Flow

class InventoryRepository(
    private val inventoryItemDao: InventoryItemDao
) {
    fun getItemsByUserId(userId: Long): Flow<List<InventoryItem>> {
        return inventoryItemDao.getItemsByUserId(userId)
    }
    
    suspend fun getItemById(id: Long): InventoryItem? {
        return inventoryItemDao.getItemById(id)
    }
    
    fun getItemsByUserIdAndCategory(userId: Long, category: String): Flow<List<InventoryItem>> {
        return inventoryItemDao.getItemsByUserIdAndCategory(userId, category)
    }
    
    suspend fun insertItem(item: InventoryItem): Long {
        return inventoryItemDao.insertItem(item)
    }
    
    suspend fun updateItem(item: InventoryItem) {
        val updatedItem = item.copy(updatedAt = System.currentTimeMillis())
        inventoryItemDao.updateItem(updatedItem)
    }
    
    suspend fun deleteItem(item: InventoryItem) {
        inventoryItemDao.deleteItem(item)
    }
    
    suspend fun deleteItemById(id: Long) {
        inventoryItemDao.deleteItemById(id)
    }
    
    fun getCategoriesByUserId(userId: Long): Flow<List<String>> {
        return inventoryItemDao.getCategoriesByUserId(userId)
    }
}