package ru.itis.newproject.hw6.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.itis.newproject.R
import ru.itis.newproject.hw6.data.entity.MovieEntity

class FavoritesAdapter(
    private var favoriteMovies: List<MovieEntity>,
    private val onItemLongClick: (MovieEntity) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.FavoriteMovieViewHolder>() {

    class FavoriteMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.movieTitle)
        private val imageView: ImageView = itemView.findViewById(R.id.movieImage)
        private val ratingTextView: TextView = itemView.findViewById(R.id.movieRate)

        fun bind(movie: MovieEntity, onItemLongClick: (MovieEntity) -> Unit) {
            titleTextView.text = movie.title
            ratingTextView.text = movie.rating.toString()

            Glide.with(itemView.context)
                .load(movie.imageUrl)
                .into(imageView)

            itemView.setOnLongClickListener {
                onItemLongClick(movie)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return FavoriteMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        val movie = favoriteMovies[position]
        holder.bind(movie, onItemLongClick)
    }

    override fun getItemCount(): Int {
        return favoriteMovies.size
    }

    fun updateFavorites(newFavorites: List<MovieEntity>) {
        favoriteMovies = newFavorites
        notifyDataSetChanged()
    }
}

