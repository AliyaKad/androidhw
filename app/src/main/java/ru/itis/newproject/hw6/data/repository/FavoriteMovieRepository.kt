package ru.itis.newproject.hw6.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.newproject.hw6.data.dao.FavoriteMovieDao
import ru.itis.newproject.hw6.data.entity.FavoriteMovieEntity

class FavoriteMovieRepository(private val favoriteMovieDao: FavoriteMovieDao) {

    suspend fun insertFavoriteMovie(favoriteMovie: FavoriteMovieEntity) {
        withContext(Dispatchers.IO) {
            favoriteMovieDao.insert(favoriteMovie)
        }
    }

    suspend fun getFavoriteMoviesByUserId(userId: Int): List<FavoriteMovieEntity> {
        return withContext(Dispatchers.IO) {
            favoriteMovieDao.getFavoriteMoviesByUserId(userId)
        }
    }

    suspend fun deleteFavoriteMovie(userId: Int, movieId: Int) {
        withContext(Dispatchers.IO) {
            favoriteMovieDao.deleteFavoriteMovie(userId, movieId)
        }
    }
}
