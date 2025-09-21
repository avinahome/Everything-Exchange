package com.everythingexchange.app.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface OfferDao {
    @Query("SELECT * FROM offers WHERE buyerId = :buyerId ORDER BY createdAt DESC")
    fun getOffersByBuyer(buyerId: Long): Flow<List<Offer>>
    
    @Query("SELECT * FROM offers WHERE sellerId = :sellerId ORDER BY createdAt DESC")
    fun getOffersBySeller(sellerId: Long): Flow<List<Offer>>
    
    @Query("SELECT * FROM offers WHERE listingId = :listingId ORDER BY createdAt DESC")
    fun getOffersForListing(listingId: Long): Flow<List<Offer>>
    
    @Query("SELECT * FROM offers WHERE id = :id")
    suspend fun getOfferById(id: Long): Offer?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOffer(offer: Offer): Long
    
    @Update
    suspend fun updateOffer(offer: Offer)
    
    @Delete
    suspend fun deleteOffer(offer: Offer)
    
    @Query("UPDATE offers SET status = :status, respondedAt = :respondedAt WHERE id = :id")
    suspend fun updateOfferStatus(id: Long, status: OfferStatus, respondedAt: Long)
}