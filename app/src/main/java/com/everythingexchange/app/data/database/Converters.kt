package com.everythingexchange.app.data.database

import androidx.room.TypeConverter

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