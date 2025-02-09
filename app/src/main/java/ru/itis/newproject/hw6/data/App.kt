package ru.itis.newproject.hw6.data

import ru.itis.newproject.hw6.data.repository.UserRepository

import android.app.Application
import androidx.room.Room

class App : Application() {

    lateinit var userRepository: UserRepository
        private set

    override fun onCreate() {
        super.onCreate()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app-database"
        ).build()

        val userDao = db.userDao()
        userRepository = UserRepository(userDao)
    }
}