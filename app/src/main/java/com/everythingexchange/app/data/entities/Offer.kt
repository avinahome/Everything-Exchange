package com.everythingexchange.app.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "offers",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["buyerId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Listing::class,
            parentColumns = ["id"],
            childColumns = ["listingId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Offer(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val listingId: Long,
    val buyerId: Long,
    val offerAmount: Double,
    val message: String? = null,
    val status: OfferStatus = OfferStatus.PENDING,
    val createdAt: Long = System.currentTimeMillis(),
    val respondedAt: Long? = null
)

enum class OfferStatus {
    PENDING, ACCEPTED, REJECTED, WITHDRAWN
}