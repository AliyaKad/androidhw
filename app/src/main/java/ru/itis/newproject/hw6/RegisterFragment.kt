package ru.itis.newproject.hw6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.newproject.MainActivity
import ru.itis.newproject.R
import ru.itis.newproject.databinding.FragmentRegisterBinding
import ru.itis.newproject.hw6.data.AppDatabase
import ru.itis.newproject.hw6.data.entity.UserEntity
import ru.itis.newproject.hw6.data.repository.UserRepository

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var userRepository: UserRepository
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
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getDatabase(requireContext())
        userRepository = UserRepository(db.userDao())

        binding.registerButton.setOnClickListener {
            val username = binding.username.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (!isEmailValid(email)) {
                Toast.makeText(context, getString(R.string.invalid_email), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                if (userRepository.isEmailExists(email)) {
                    Toast.makeText(context, getString(R.string.email_already_exists), Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val user = UserEntity(username = username, email = email, password = password)
                val id = insertUser(user)
                if (id != -1L) {
                    userViewModel.setUserId(id.toInt())
                    (requireActivity() as MainActivity).setUserId(id.toString())
                    navigateToFavoritesScreen()
                }
            }
        }
    }

    private suspend fun insertUser(user: UserEntity): Long =
        withContext(Dispatchers.IO) { userRepository.insertUser(user) }

    private fun isEmailValid(email: String): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

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