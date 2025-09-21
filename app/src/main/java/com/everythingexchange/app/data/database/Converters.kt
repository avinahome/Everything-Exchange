package com.everythingexchange.app.data.database

import androidx.room.TypeConverter
import com.everythingexchange.app.data.entities.OfferStatus

class Converters {
    @TypeConverter
    fun fromOfferStatus(status: OfferStatus): String {
        return status.name
    }

    @TypeConverter
    fun toOfferStatus(status: String): OfferStatus {
        return OfferStatus.valueOf(status)
    }
}