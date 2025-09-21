package com.everythingexchange.app.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context

@Database(
    entities = [
        User::class,
        InventoryItem::class,
        Listing::class,
        Offer::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    abstract fun inventoryItemDao(): InventoryItemDao
    abstract fun listingDao(): ListingDao
    abstract fun offerDao(): OfferDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "everything_exchange_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}