package com.everythingexchange.app

import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.*
import kotlinx.coroutines.test.runTest
import com.everythingexchange.app.data.repository.UserRepository
import com.everythingexchange.app.data.database.UserDao
import com.everythingexchange.app.data.database.User

/**
 * Unit tests for UserRepository
 */
class UserRepositoryTest {
    
    @Test
    fun createAccount_createsUserCorrectly() = runTest {
        val mockDao = mock(UserDao::class.java)
        val repository = UserRepository(mockDao)
        
        `when`(mockDao.insertUser(any())).thenReturn(1L)
        
        val userId = repository.createAccount("testuser", "test@example.com", "Test City")
        
        assertEquals(1L, userId)
        verify(mockDao).insertUser(any())
    }
}