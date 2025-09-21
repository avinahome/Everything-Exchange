package com.everythingexchange.app.repository

import com.everythingexchange.app.data.dao.UserDao
import com.everythingexchange.app.data.entities.User
import com.everythingexchange.app.utils.HashUtils
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val userDao: UserDao
) {
    suspend fun authenticate(username: String, password: String): User? {
        val passwordHash = HashUtils.hashPassword(password)
        return userDao.authenticate(username, passwordHash)
    }
    
    suspend fun registerUser(username: String, password: String, email: String?): Result<User> {
        return try {
            // Check if username already exists
            val existingUser = userDao.getUserByUsername(username)
            if (existingUser != null) {
                return Result.failure(Exception("Username already exists"))
            }
            
            val passwordHash = HashUtils.hashPassword(password)
            val user = User(
                username = username,
                passwordHash = passwordHash,
                email = email
            )
            val userId = userDao.insertUser(user)
            val newUser = user.copy(id = userId)
            Result.success(newUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getUserById(id: Long): User? {
        return userDao.getUserById(id)
    }
    
    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }
    
    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
    }
}