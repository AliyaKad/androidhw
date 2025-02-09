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

class MoviesAdapter(private val movies: MutableList<MovieEntity>, private val onMovieLongClick: (MovieEntity) -> Unit) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnLongClickListener {
            onMovieLongClick(movie)
            true
        }
    }

    override fun getItemCount(): Int = movies.size

    fun updateMovies(newMovies: List<MovieEntity>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }



    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.movieTitle)
        private val imageView: ImageView = itemView.findViewById(R.id.movieImage)
        private val ratingTextView: TextView = itemView.findViewById(R.id.movieRate)

        fun bind(movie: MovieEntity) {
            titleTextView.text = movie.title
            ratingTextView.text = movie.rating.toString()

            Glide.with(itemView.context)
                .load(movie.imageUrl)
                .into(imageView)
        }
    }

}

