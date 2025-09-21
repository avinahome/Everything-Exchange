package com.everythingexchange.app.repository

import com.everythingexchange.app.data.dao.ListingDao
import com.everythingexchange.app.data.entities.Listing
import kotlinx.coroutines.flow.Flow

class ListingRepository(
    private val listingDao: ListingDao
) {
    fun getListingsBySellerId(sellerId: Long): Flow<List<Listing>> {
        return listingDao.getListingsBySellerId(sellerId)
    }
    
    fun getActiveListingsExcludingUser(excludeUserId: Long): Flow<List<Listing>> {
        return listingDao.getActiveListingsExcludingUser(excludeUserId)
    }
    
    suspend fun getListingById(id: Long): Listing? {
        return listingDao.getListingById(id)
    }
    
    suspend fun getListingByItemId(itemId: Long): Listing? {
        return listingDao.getListingByItemId(itemId)
    }
    
    suspend fun insertListing(listing: Listing): Long {
        return listingDao.insertListing(listing)
    }
    
    suspend fun updateListing(listing: Listing) {
        val updatedListing = listing.copy(updatedAt = System.currentTimeMillis())
        listingDao.updateListing(updatedListing)
    }
    
    suspend fun deleteListing(listing: Listing) {
        listingDao.deleteListing(listing)
    }
    
    suspend fun deactivateListing(id: Long) {
        listingDao.deactivateListing(id)
    }
}