package com.everythingexchange.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.everythingexchange.app.data.entities.Listing
import com.everythingexchange.app.repository.ListingRepository
import com.everythingexchange.app.utils.SessionManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ListingViewModel(
    private val listingRepository: ListingRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ListingUiState())
    val uiState: StateFlow<ListingUiState> = _uiState.asStateFlow()
    
    val userListings = listingRepository.getListingsBySellerId(sessionManager.getCurrentUserId())
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    
    fun createListing(
        itemId: Long,
        title: String,
        description: String,
        price: Double
    ) {
        if (title.isBlank() || description.isBlank() || price <= 0) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Please fill in all fields with valid values"
            )
            return
        }
        
        val listing = Listing(
            itemId = itemId,
            sellerId = sessionManager.getCurrentUserId(),
            title = title,
            description = description,
            price = price
        )
        
        viewModelScope.launch {
            try {
                listingRepository.insertListing(listing)
                _uiState.value = _uiState.value.copy(
                    successMessage = "Listing created successfully"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to create listing: ${e.message}"
                )
            }
        }
    }
    
    fun deactivateListing(listingId: Long) {
        viewModelScope.launch {
            try {
                listingRepository.deactivateListing(listingId)
                _uiState.value = _uiState.value.copy(
                    successMessage = "Listing deactivated"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to deactivate listing: ${e.message}"
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

data class ListingUiState(
    val errorMessage: String? = null,
    val successMessage: String? = null
)