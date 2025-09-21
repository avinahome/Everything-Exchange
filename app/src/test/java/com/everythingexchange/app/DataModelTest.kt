package com.everythingexchange.app

import org.junit.Test
import org.junit.Assert.*
import com.everythingexchange.app.data.database.User
import com.everythingexchange.app.data.database.InventoryItem
import com.everythingexchange.app.data.database.OfferStatus

/**
 * Unit tests for Everything Exchange data models
 */
class DataModelTest {
    
    @Test
    fun user_creation_isCorrect() {
        val user = User(
            id = 1,
            username = "testuser",
            email = "test@example.com",
            location = "Test City"
        )
        
        assertEquals("testuser", user.username)
        assertEquals("test@example.com", user.email)
        assertEquals("Test City", user.location)
        assertTrue(user.createdAt > 0)
    }
    
    @Test
    fun inventoryItem_creation_isCorrect() {
        val item = InventoryItem(
            id = 1,
            userId = 1,
            name = "Test Item",
            description = "Test Description",
            category = "Electronics",
            price = 99.99
        )
        
        assertEquals("Test Item", item.name)
        assertEquals("Test Description", item.description)
        assertEquals("Electronics", item.category)
        assertEquals(99.99, item.price, 0.01)
    }
    
    @Test
    fun offerStatus_enum_isCorrect() {
        assertEquals("PENDING", OfferStatus.PENDING.name)
        assertEquals("ACCEPTED", OfferStatus.ACCEPTED.name)
        assertEquals("DECLINED", OfferStatus.DECLINED.name)
        assertEquals("EXPIRED", OfferStatus.EXPIRED.name)
    }
}