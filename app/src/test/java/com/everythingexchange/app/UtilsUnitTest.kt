package com.everythingexchange.app

import com.everythingexchange.app.utils.HashUtils
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for utility functions
 */
class UtilsUnitTest {
    @Test
    fun hashPassword_isCorrect() {
        val password = "testpassword123"
        val hash1 = HashUtils.hashPassword(password)
        val hash2 = HashUtils.hashPassword(password)
        
        // Same password should produce same hash
        assertEquals(hash1, hash2)
        
        // Hash should not be empty
        assertTrue(hash1.isNotEmpty())
        
        // Hash should be different from original password
        assertNotEquals(password, hash1)
    }
    
    @Test
    fun hashPassword_differentPasswords() {
        val password1 = "password1"
        val password2 = "password2"
        
        val hash1 = HashUtils.hashPassword(password1)
        val hash2 = HashUtils.hashPassword(password2)
        
        // Different passwords should produce different hashes
        assertNotEquals(hash1, hash2)
    }
}