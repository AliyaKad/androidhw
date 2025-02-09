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
import ru.itis.newproject.MainActivity
import ru.itis.newproject.R
import ru.itis.newproject.databinding.FragmentFavouritesBinding
import ru.itis.newproject.hw6.adapter.FavoritesAdapter
import ru.itis.newproject.hw6.data.AppDatabase
import ru.itis.newproject.hw6.data.entity.MovieEntity
import ru.itis.newproject.hw6.data.repository.FavoriteMovieRepository
import ru.itis.newproject.hw6.data.repository.MovieRepository

class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoritesAdapter: FavoritesAdapter
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
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getDatabase(requireContext())
        movieRepository = MovieRepository(db.movieDao())
        favoriteMovieRepository = FavoriteMovieRepository(db.favoriteMovieDao())

        favoritesAdapter = FavoritesAdapter(mutableListOf()) { movie ->
            deleteFavoriteMovie(movie)
        }
        binding.recyclerViewFavorites.apply {
            adapter = favoritesAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.goToMainButton.setOnClickListener { navigateToMainScreen() }
        binding.logoutButton.setOnClickListener { logoutUser() }
        binding.profileButton.setOnClickListener { navigateToProfileScreen() }

        loadFavorites()
    }

    private fun loadFavorites() {
        viewLifecycleOwner.lifecycleScope.launch {
            val userId = userViewModel.userId.value
            if (userId != null) {
                val favoriteMovies = favoriteMovieRepository.getFavoriteMoviesByUserId(userId)
                val movieIds = favoriteMovies.map { it.movieId }
                val movies = movieIds.mapNotNull { movieId -> movieRepository.getMovieById(movieId) }
                favoritesAdapter.updateFavorites(movies)
            }
        }
    }

    private fun navigateToMainScreen() {
        val mainFragment = MainFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, mainFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun logoutUser() {
        userViewModel.clearUserId()
        (requireActivity() as MainActivity).clearUserId()
        navigateToLoginScreen()
    }

    private fun navigateToLoginScreen() {
        val loginFragment = LoginFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, loginFragment)
            .commit()
    }

    private fun deleteFavoriteMovie(movie: MovieEntity) {
        viewLifecycleOwner.lifecycleScope.launch {
            val userId = userViewModel.userId.value
            if (userId != null) {
                favoriteMovieRepository.deleteFavoriteMovie(userId, movie.id)
                loadFavorites()
                Toast.makeText(context, getString(R.string.movie_deleted), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToProfileScreen() {
        val profileFragment = ProfileFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, profileFragment)
            .commit()
        requireActivity().invalidateOptionsMenu()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}