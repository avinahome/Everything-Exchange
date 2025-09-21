package com.everythingexchange.app.data.dao

import androidx.room.*
import com.everythingexchange.app.data.entities.Listing
import kotlinx.coroutines.flow.Flow

@Dao
interface ListingDao {
    @Query("SELECT * FROM listings WHERE sellerId = :sellerId ORDER BY updatedAt DESC")
    fun getListingsBySellerId(sellerId: Long): Flow<List<Listing>>
    
    @Query("SELECT * FROM listings WHERE isActive = 1 AND sellerId != :excludeUserId ORDER BY updatedAt DESC")
    fun getActiveListingsExcludingUser(excludeUserId: Long): Flow<List<Listing>>
    
    @Query("SELECT * FROM listings WHERE id = :id")
    suspend fun getListingById(id: Long): Listing?
    
    @Query("SELECT * FROM listings WHERE itemId = :itemId")
    suspend fun getListingByItemId(itemId: Long): Listing?
    
    @Insert
    suspend fun insertListing(listing: Listing): Long
    
    @Update
    suspend fun updateListing(listing: Listing)
    
    @Delete
    suspend fun deleteListing(listing: Listing)
    
    @Query("UPDATE listings SET isActive = 0 WHERE id = :id")
    suspend fun deactivateListing(id: Long)
}