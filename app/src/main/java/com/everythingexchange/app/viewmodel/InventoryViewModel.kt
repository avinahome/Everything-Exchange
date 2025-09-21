package com.everythingexchange.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.everythingexchange.app.data.entities.InventoryItem
import com.everythingexchange.app.repository.InventoryRepository
import com.everythingexchange.app.utils.SessionManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class InventoryViewModel(
    private val inventoryRepository: InventoryRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(InventoryUiState())
    val uiState: StateFlow<InventoryUiState> = _uiState.asStateFlow()
    
    val inventoryItems = inventoryRepository.getItemsByUserId(sessionManager.getCurrentUserId())
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    
    val categories = inventoryRepository.getCategoriesByUserId(sessionManager.getCurrentUserId())
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    
    fun addItem(
        name: String,
        description: String,
        category: String,
        purchasePrice: Double?,
        currentValue: Double?,
        condition: String,
        photoUri: String?
    ) {
        if (name.isBlank() || description.isBlank() || category.isBlank()) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Please fill in all required fields"
            )
            return
        }
        
        val item = InventoryItem(
            userId = sessionManager.getCurrentUserId(),
            name = name,
            description = description,
            category = category,
            purchasePrice = purchasePrice,
            currentValue = currentValue,
            condition = condition,
            photoUri = photoUri
        )
        
        viewModelScope.launch {
            try {
                inventoryRepository.insertItem(item)
                _uiState.value = _uiState.value.copy(
                    successMessage = "Item added successfully"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to add item: ${e.message}"
                )
            }
        }
    }
    
    fun updateItem(item: InventoryItem) {
        viewModelScope.launch {
            try {
                inventoryRepository.updateItem(item)
                _uiState.value = _uiState.value.copy(
                    successMessage = "Item updated successfully"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to update item: ${e.message}"
                )
            }
        }
    }
    
    fun deleteItem(item: InventoryItem) {
        viewModelScope.launch {
            try {
                inventoryRepository.deleteItem(item)
                _uiState.value = _uiState.value.copy(
                    successMessage = "Item deleted successfully"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to delete item: ${e.message}"
                )
            }
        }
    }
    
    fun clearMessages() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null,
            successMessage = null
        )
    }
}

data class InventoryUiState(
    val errorMessage: String? = null,
    val successMessage: String? = null
)