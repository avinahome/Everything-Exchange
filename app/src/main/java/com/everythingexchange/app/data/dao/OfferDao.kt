package com.everythingexchange.app.data.dao

import androidx.room.*
import com.everythingexchange.app.data.entities.Offer
import com.everythingexchange.app.data.entities.OfferStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface OfferDao {
    @Query("SELECT * FROM offers WHERE buyerId = :buyerId ORDER BY createdAt DESC")
    fun getOffersByBuyerId(buyerId: Long): Flow<List<Offer>>
    
    @Query("SELECT * FROM offers WHERE listingId = :listingId ORDER BY createdAt DESC")
    fun getOffersByListingId(listingId: Long): Flow<List<Offer>>
    
    @Query("SELECT * FROM offers WHERE id = :id")
    suspend fun getOfferById(id: Long): Offer?
    
    @Insert
    suspend fun insertOffer(offer: Offer): Long
    
    @Update
    suspend fun updateOffer(offer: Offer)
    
    @Delete
    suspend fun deleteOffer(offer: Offer)
    
    @Query("UPDATE offers SET status = :status, respondedAt = :respondedAt WHERE id = :id")
    suspend fun updateOfferStatus(id: Long, status: OfferStatus, respondedAt: Long)
}