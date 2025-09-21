package com.everythingexchange.app.data.repository

import com.everythingexchange.app.data.database.Listing
import com.everythingexchange.app.data.database.ListingDao
import kotlinx.coroutines.flow.Flow

class ListingRepository(private val listingDao: ListingDao) {
    
    fun getActiveListings(): Flow<List<Listing>> = listingDao.getActiveListings()
    
    fun getListingsBySeller(sellerId: Long): Flow<List<Listing>> = 
        listingDao.getListingsBySeller(sellerId)
    
    suspend fun getListingById(id: Long): Listing? = listingDao.getListingById(id)
    
    suspend fun insertListing(listing: Listing): Long = listingDao.insertListing(listing)
    
    suspend fun updateListing(listing: Listing) = listingDao.updateListing(listing)
    
    suspend fun deleteListing(listing: Listing) = listingDao.deleteListing(listing)
    
    suspend fun deactivateListing(id: Long) = listingDao.deactivateListing(id)
    
    suspend fun createListing(
        itemId: Long,
        sellerId: Long,
        title: String,
        description: String,
        price: Double
    ): Long {
        val listing = Listing(
            itemId = itemId,
            sellerId = sellerId,
            title = title,
            description = description,
            price = price
        )
        return insertListing(listing)
    }
}