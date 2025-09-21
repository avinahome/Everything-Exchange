package com.everythingexchange.app.data.repository

import com.everythingexchange.app.data.database.Offer
import com.everythingexchange.app.data.database.OfferDao
import com.everythingexchange.app.data.database.OfferStatus
import kotlinx.coroutines.flow.Flow

class OfferRepository(private val offerDao: OfferDao) {
    
    fun getOffersByBuyer(buyerId: Long): Flow<List<Offer>> = offerDao.getOffersByBuyer(buyerId)
    
    fun getOffersBySeller(sellerId: Long): Flow<List<Offer>> = offerDao.getOffersBySeller(sellerId)
    
    fun getOffersForListing(listingId: Long): Flow<List<Offer>> = offerDao.getOffersForListing(listingId)
    
    suspend fun getOfferById(id: Long): Offer? = offerDao.getOfferById(id)
    
    suspend fun insertOffer(offer: Offer): Long = offerDao.insertOffer(offer)
    
    suspend fun updateOffer(offer: Offer) = offerDao.updateOffer(offer)
    
    suspend fun deleteOffer(offer: Offer) = offerDao.deleteOffer(offer)
    
    suspend fun updateOfferStatus(id: Long, status: OfferStatus) = 
        offerDao.updateOfferStatus(id, status, System.currentTimeMillis())
    
    suspend fun makeOffer(
        listingId: Long,
        buyerId: Long,
        sellerId: Long,
        amount: Double,
        message: String? = null
    ): Long {
        val offer = Offer(
            listingId = listingId,
            buyerId = buyerId,
            sellerId = sellerId,
            amount = amount,
            message = message
        )
        return insertOffer(offer)
    }
    
    suspend fun acceptOffer(offerId: Long) = updateOfferStatus(offerId, OfferStatus.ACCEPTED)
    
    suspend fun declineOffer(offerId: Long) = updateOfferStatus(offerId, OfferStatus.DECLINED)
}