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
import ru.itis.newproject.databinding.FragmentLoginBinding
import ru.itis.newproject.hw6.data.App
import ru.itis.newproject.hw6.data.entity.UserEntity
import ru.itis.newproject.hw6.data.repository.UserRepository

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var userRepository: UserRepository
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        userRepository = (requireActivity().application as App).userRepository

        binding.loginButton.setOnClickListener {
            val identifier = binding.email.text.toString()
            val password = binding.password.text.toString()

            lifecycleScope.launch {
                val user = getUser(identifier, password)
                if (user != null) {
                    userViewModel.setUserId(user.id.toInt())
                    (requireActivity() as MainActivity).setUserId(user.id.toString())
                    navigateToFavouritesScreen()
                } else {
                    Toast.makeText(context, getString(R.string.invalid_credentials), Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.registerButton.setOnClickListener {
            navigateToRegisterScreen()
        }
    }

    private suspend fun getUser(identifier: String, password: String): UserEntity? =
        withContext(Dispatchers.IO) { userRepository.getUser(identifier, password) }

    private fun navigateToFavouritesScreen() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FavouritesFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToRegisterScreen() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, RegisterFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
