package ru.itis.newproject.hw6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.itis.newproject.R
import ru.itis.newproject.databinding.FragmentAccountBinding
import ru.itis.newproject.hw6.data.App
import ru.itis.newproject.hw6.data.entity.UserEntity
import ru.itis.newproject.hw6.data.repository.UserRepository

class ProfileFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var userRepository: UserRepository
    private lateinit var userViewModel: UserViewModel
    private lateinit var currentUser: UserEntity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        userRepository = (requireActivity().application as App).userRepository

        loadUserData()
        setupChangeUsername()
        setupChangeEmail()
        setupChangePassword()
    }

    private fun loadUserData() {
        userViewModel.userId.observe(viewLifecycleOwner) { userId ->
            if (userId != null && userId != -1) {
                lifecycleScope.launch {
                    currentUser = userRepository.getUserById(userId)
                    currentUser?.let {
                        binding.tvUsername.text = getString(R.string.username_format, it.username)
                        binding.tvEmail.text = getString(R.string.email_format, it.email)
                        enableChangeButtons(true)
                    }
                }
            }
        }
    }

    private fun enableChangeButtons(enable: Boolean) {
        binding.btnChangeUsername.isEnabled = enable
        binding.btnChangeEmail.isEnabled = enable
        binding.btnChangePassword.isEnabled = enable
    }

    private fun setupChangeUsername() {
        binding.btnChangeUsername.setOnClickListener {
            showChangeLayout(binding.llChangeUsername)
        }

        binding.btnSaveUsername.setOnClickListener {
            val newUsername = binding.etNewUsername.text.toString()
            val confirmPassword = binding.etConfirmPasswordForUsername.text.toString()

            if (confirmPassword == currentUser.password) {
                lifecycleScope.launch {
                    val updatedUser = currentUser.copy(username = newUsername)
                    userRepository.updateUser(updatedUser)
                    currentUser = updatedUser
                    binding.tvUsername.text = getString(R.string.username_format, currentUser.username)
                    hideChangeLayout(binding.llChangeUsername)
                    Toast.makeText(context, getString(R.string.username_updated), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, getString(R.string.invalid_password), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupChangeEmail() {
        binding.btnChangeEmail.setOnClickListener {
            showChangeLayout(binding.llChangeEmail)
        }

        binding.btnSaveEmail.setOnClickListener {
            val newEmail = binding.etNewEmail.text.toString()
            val confirmPassword = binding.etConfirmPasswordForEmail.text.toString()

            if (confirmPassword == currentUser.password) {
                lifecycleScope.launch {
                    if (!userRepository.isEmailExists(newEmail)) {
                        val updatedUser = currentUser.copy(email = newEmail)
                        userRepository.updateUser(updatedUser)
                        currentUser = updatedUser
                        binding.tvEmail.text = getString(R.string.email_format, currentUser.email)
                        hideChangeLayout(binding.llChangeEmail)
                        Toast.makeText(context, getString(R.string.email_updated), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, getString(R.string.email_already_exists), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, getString(R.string.invalid_password), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupChangePassword() {
        binding.btnChangePassword.setOnClickListener {
            showChangeLayout(binding.llChangePassword)
        }

        binding.btnSavePassword.setOnClickListener {
            val oldPassword = binding.etOldPassword.text.toString()
            val newPassword = binding.etNewPassword.text.toString()
            val confirmNewPassword = binding.etConfirmNewPassword.text.toString()

            if (oldPassword == currentUser.password && newPassword == confirmNewPassword) {
                lifecycleScope.launch {
                    val updatedUser = currentUser.copy(password = newPassword)
                    userRepository.updateUser(updatedUser)
                    currentUser = updatedUser
                    hideChangeLayout(binding.llChangePassword)
                    Toast.makeText(context, getString(R.string.password_updated), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, getString(R.string.password_update_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showChangeLayout(layout: LinearLayout) {
        layout.visibility = LinearLayout.VISIBLE
        listOf(binding.llChangeUsername, binding.llChangeEmail, binding.llChangePassword)
            .filter { it != layout }
            .forEach { it.visibility = LinearLayout.GONE }
    }

    private fun hideChangeLayout(layout: LinearLayout) {
        layout.visibility = LinearLayout.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

