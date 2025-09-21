package com.everythingexchange.app.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ListingDao {
    @Query("SELECT * FROM listings WHERE isActive = 1 ORDER BY createdAt DESC")
    fun getActiveListings(): Flow<List<Listing>>
    
    @Query("SELECT * FROM listings WHERE sellerId = :sellerId ORDER BY createdAt DESC")
    fun getListingsBySeller(sellerId: Long): Flow<List<Listing>>
    
    @Query("SELECT * FROM listings WHERE id = :id")
    suspend fun getListingById(id: Long): Listing?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListing(listing: Listing): Long
    
    @Update
    suspend fun updateListing(listing: Listing)
    
    @Delete
    suspend fun deleteListing(listing: Listing)
    
    @Query("UPDATE listings SET isActive = 0 WHERE id = :id")
    suspend fun deactivateListing(id: Long)
}