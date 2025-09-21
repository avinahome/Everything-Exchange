package com.everythingexchange.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.everythingexchange.app.data.database.InventoryItem
import com.everythingexchange.app.data.repository.InventoryRepository
import com.everythingexchange.app.utils.ExportUtils
import com.everythingexchange.app.utils.PhotoUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class InventoryViewModel(
    private val inventoryRepository: InventoryRepository,
    private val photoUtils: PhotoUtils,
    private val exportUtils: ExportUtils
) : ViewModel() {
    
    private val _items = MutableStateFlow<List<InventoryItem>>(emptyList())
    val items: StateFlow<List<InventoryItem>> = _items.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    private val _exportResult = MutableStateFlow<File?>(null)
    val exportResult: StateFlow<File?> = _exportResult.asStateFlow()
    
    fun loadItemsForUser(userId: Long) {
        viewModelScope.launch {
            inventoryRepository.getItemsByUser(userId).collect { itemList ->
                _items.value = itemList
            }
        }
    }
    
    fun addItem(
        userId: Long,
        name: String,
        description: String,
        category: String,
        price: Double? = null,
        imagePath: String? = null
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                inventoryRepository.createItem(userId, name, description, category, price, imagePath)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun updateItem(item: InventoryItem) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                inventoryRepository.updateItem(item.copy(updatedAt = System.currentTimeMillis()))
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun deleteItem(item: InventoryItem) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Delete associated image if exists
                item.imagePath?.let { photoUtils.deleteImage(it) }
                inventoryRepository.deleteItem(item)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun exportToCsv() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val file = exportUtils.exportInventoryToCsv(_items.value)
                _exportResult.value = file
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun exportToPdf() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val file = exportUtils.exportInventoryToPdf(_items.value)
                _exportResult.value = file
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun clearError() {
        _error.value = null
    }
    
    fun clearExportResult() {
        _exportResult.value = null
    }
}