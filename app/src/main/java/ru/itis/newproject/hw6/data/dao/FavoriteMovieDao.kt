package ru.itis.newproject.hw6.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.itis.newproject.hw6.data.entity.FavoriteMovieEntity

@Dao
interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteMovie: FavoriteMovieEntity)

    @Query("SELECT * FROM favorite_movies WHERE user_id = :userId")
    suspend fun getFavoriteMoviesByUserId(userId: Int): List<FavoriteMovieEntity>

    @Query("DELETE FROM favorite_movies WHERE user_id = :userId AND movie_id = :movieId")
    suspend fun deleteFavoriteMovie(userId: Int, movieId: Int)

    @Query("SELECT COUNT(*) FROM favorite_movies WHERE user_id = :userId AND movie_id = :movieId")
    suspend fun countFavoriteMovie(userId: Int, movieId: Int): Int
}
