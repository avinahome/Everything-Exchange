package com.everythingexchange.app.data.repository

import com.everythingexchange.app.data.database.InventoryItem
import com.everythingexchange.app.data.database.InventoryItemDao
import kotlinx.coroutines.flow.Flow

class InventoryRepository(private val inventoryItemDao: InventoryItemDao) {
    
    fun getItemsByUser(userId: Long): Flow<List<InventoryItem>> = 
        inventoryItemDao.getItemsByUser(userId)
    
    suspend fun getItemById(id: Long): InventoryItem? = inventoryItemDao.getItemById(id)
    
    fun searchItems(searchTerm: String): Flow<List<InventoryItem>> = 
        inventoryItemDao.searchItems("%$searchTerm%")
    
    suspend fun insertItem(item: InventoryItem): Long = inventoryItemDao.insertItem(item)
    
    suspend fun updateItem(item: InventoryItem) = inventoryItemDao.updateItem(item)
    
    suspend fun deleteItem(item: InventoryItem) = inventoryItemDao.deleteItem(item)
    
    fun getAllItems(): Flow<List<InventoryItem>> = inventoryItemDao.getAllItems()
    
    suspend fun createItem(
        userId: Long,
        name: String,
        description: String,
        category: String,
        price: Double? = null,
        imagePath: String? = null
    ): Long {
        val item = InventoryItem(
            userId = userId,
            name = name,
            description = description,
            category = category,
            price = price,
            imagePath = imagePath
        )
        return insertItem(item)
    }
}