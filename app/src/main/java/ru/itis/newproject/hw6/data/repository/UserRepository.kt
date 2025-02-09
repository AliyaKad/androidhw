package ru.itis.newproject.hw6.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.newproject.hw6.data.dao.UserDao
import ru.itis.newproject.hw6.data.entity.UserEntity

class UserRepository(private val userDao: UserDao) {
    suspend fun getUser(identifier: String, password: String): UserEntity? {
        return withContext(Dispatchers.IO) {
            userDao.getUser (identifier, password)
        }
    }

    suspend fun insertUser(user: UserEntity): Long {
        return withContext(Dispatchers.IO) {
            userDao.insertUser (user)
        }
    }

    suspend fun isEmailExists(email: String): Boolean {
        return withContext(Dispatchers.IO) {
            userDao.isEmailExists(email) > 0
        }
    }
    suspend fun getUserById(userId: Int): UserEntity {
        return withContext(Dispatchers.IO) {
            userDao.getUserById(userId)
        }
    }
    suspend fun updateUser(user: UserEntity) {
        return withContext(Dispatchers.IO) {
            userDao.updateUser (user)
        }
    }
}