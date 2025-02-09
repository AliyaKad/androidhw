package ru.itis.newproject.hw6.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.itis.newproject.hw6.data.dao.FavoriteMovieDao
import ru.itis.newproject.hw6.data.dao.MovieDao
import ru.itis.newproject.hw6.data.dao.UserDao
import ru.itis.newproject.hw6.data.entity.FavoriteMovieEntity
import ru.itis.newproject.hw6.data.entity.MovieEntity
import ru.itis.newproject.hw6.data.entity.UserEntity

@Database(entities = [UserEntity::class, MovieEntity::class, FavoriteMovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun movieDao(): MovieDao
    abstract fun favoriteMovieDao(): FavoriteMovieDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(roomCallback)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    populateMovies(INSTANCE!!)
                }
            }
        }

        private suspend fun populateMovies(db: AppDatabase) {
            val movieDao = db.movieDao()
            if (movieDao.getAllMovies().value.isNullOrEmpty()) {
                movieDao.insertAll(MovieData.movies)
            }
        }

    }
}


