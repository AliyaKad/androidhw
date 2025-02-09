package ru.itis.newproject.hw6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.itis.newproject.R
import ru.itis.newproject.databinding.FragmentMainBinding
import ru.itis.newproject.hw6.adapter.MoviesAdapter
import ru.itis.newproject.hw6.data.AppDatabase
import ru.itis.newproject.hw6.data.entity.FavoriteMovieEntity
import ru.itis.newproject.hw6.data.entity.MovieEntity
import ru.itis.newproject.hw6.data.repository.FavoriteMovieRepository
import ru.itis.newproject.hw6.data.repository.MovieRepository

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var movieRepository: MovieRepository
    private lateinit var favoriteMovieRepository: FavoriteMovieRepository
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getDatabase(requireContext())
        movieRepository = MovieRepository(db.movieDao())
        favoriteMovieRepository = FavoriteMovieRepository(db.favoriteMovieDao())

        moviesAdapter = MoviesAdapter(mutableListOf()) { movie ->
            saveToFavorites(movie)
        }
        binding.recyclerView.apply {
            adapter = moviesAdapter
            layoutManager = LinearLayoutManager(context)
        }

        loadMovies()

        binding.favoritesButton.setOnClickListener { navigateToFavoritesScreen() }
    }

    private fun loadMovies() {
        movieRepository.getAllMovies().observe(viewLifecycleOwner) { movies ->
            moviesAdapter.updateMovies(movies)
        }
    }

    private fun saveToFavorites(movie: MovieEntity) {
        val userId = userViewModel.userId.value ?: return
        viewLifecycleOwner.lifecycleScope.launch {
            val favoriteMovie = FavoriteMovieEntity(userId = userId, movieId = movie.id)
            val count = favoriteMovieRepository.getFavoriteMoviesByUserId(userId).count { it.movieId == movie.id }
            if (count == 0) {
                favoriteMovieRepository.insertFavoriteMovie(favoriteMovie)
                Toast.makeText(context, getString(R.string.movie_added_to_favorites), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, getString(R.string.movie_already_in_favorites), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToFavoritesScreen() {
        val favoritesFragment = FavouritesFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, favoritesFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}