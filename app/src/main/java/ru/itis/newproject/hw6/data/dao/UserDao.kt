package ru.itis.newproject.hw6.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.itis.newproject.hw6.data.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE (email = :identifier) AND password = :password")
    fun getUser(identifier: String, password: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser (user: UserEntity): Long

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    fun isEmailExists(email: String): Int

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int): UserEntity

    @Update
    suspend fun updateUser(user: UserEntity)
}

