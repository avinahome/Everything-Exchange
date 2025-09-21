package com.everythingexchange.app

import android.app.Application
import com.everythingexchange.app.data.database.AppDatabase
import com.everythingexchange.app.repository.*

class EverythingExchangeApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val userRepository by lazy { UserRepository(database.userDao()) }
    val inventoryRepository by lazy { InventoryRepository(database.inventoryItemDao()) }
    val listingRepository by lazy { ListingRepository(database.listingDao()) }
    val offerRepository by lazy { OfferRepository(database.offerDao()) }
}