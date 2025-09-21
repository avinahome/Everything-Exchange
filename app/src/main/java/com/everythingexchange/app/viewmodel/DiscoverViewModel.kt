package com.everythingexchange.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.everythingexchange.app.data.entities.Listing
import com.everythingexchange.app.repository.ListingRepository
import com.everythingexchange.app.utils.SessionManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DiscoverViewModel(
    private val listingRepository: ListingRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    val availableListings = listingRepository.getActiveListingsExcludingUser(sessionManager.getCurrentUserId())
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    // Filter listings based on search query
    val filteredListings = combine(availableListings, searchQuery) { listings, query ->
        if (query.isBlank()) {
            listings
        } else {
            listings.filter { listing ->
                listing.title.contains(query, ignoreCase = true) ||
                listing.description.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )
}