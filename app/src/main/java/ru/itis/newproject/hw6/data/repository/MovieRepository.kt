package ru.itis.newproject.hw6.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.newproject.hw6.data.dao.MovieDao
import ru.itis.newproject.hw6.data.entity.MovieEntity

class MovieRepository(private val movieDao: MovieDao) {

    fun getAllMovies(): LiveData<List<MovieEntity>> {
        return movieDao.getAllMovies()
    }

    suspend fun insertMovies(movies: List<MovieEntity>) {
        withContext(Dispatchers.IO) {
            movieDao.insertAll(movies)
        }
    }

    suspend fun getMovieById(movieId: Int): MovieEntity? {
        return withContext(Dispatchers.IO) {
            movieDao.getMovieById(movieId)
        }
    }
}
