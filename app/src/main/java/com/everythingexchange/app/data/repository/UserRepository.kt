package com.everythingexchange.app.data.repository

import com.everythingexchange.app.data.database.User
import com.everythingexchange.app.data.database.UserDao
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    
    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()
    
    suspend fun getUserById(id: Long): User? = userDao.getUserById(id)
    
    suspend fun getUserByUsername(username: String): User? = userDao.getUserByUsername(username)
    
    suspend fun insertUser(user: User): Long = userDao.insertUser(user)
    
    suspend fun updateUser(user: User) = userDao.updateUser(user)
    
    suspend fun deleteUser(user: User) = userDao.deleteUser(user)
    
    suspend fun getCurrentUser(): User? = userDao.getCurrentUser()
    
    suspend fun createAccount(username: String, email: String, location: String): Long {
        val user = User(
            username = username,
            email = email,
            location = location
        )
        return insertUser(user)
    }
}