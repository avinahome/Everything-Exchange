package com.everythingexchange.app.repository

import com.everythingexchange.app.data.dao.OfferDao
import com.everythingexchange.app.data.entities.Offer
import com.everythingexchange.app.data.entities.OfferStatus
import kotlinx.coroutines.flow.Flow

class OfferRepository(
    private val offerDao: OfferDao
) {
    fun getOffersByBuyerId(buyerId: Long): Flow<List<Offer>> {
        return offerDao.getOffersByBuyerId(buyerId)
    }
    
    fun getOffersByListingId(listingId: Long): Flow<List<Offer>> {
        return offerDao.getOffersByListingId(listingId)
    }
    
    suspend fun getOfferById(id: Long): Offer? {
        return offerDao.getOfferById(id)
    }
    
    suspend fun insertOffer(offer: Offer): Long {
        return offerDao.insertOffer(offer)
    }
    
    suspend fun updateOffer(offer: Offer) {
        offerDao.updateOffer(offer)
    }
    
    suspend fun deleteOffer(offer: Offer) {
        offerDao.deleteOffer(offer)
    }
    
    suspend fun updateOfferStatus(id: Long, status: OfferStatus) {
        val respondedAt = if (status != OfferStatus.PENDING) {
            System.currentTimeMillis()
        } else null
        offerDao.updateOfferStatus(id, status, respondedAt ?: 0)
    }
}