package ru.itis.newproject.hw4

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.itis.newproject.MainActivity
import ru.itis.newproject.R
import ru.itis.newproject.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var viewBinding: FragmentNotificationsBinding? = null
    private val binding get() = viewBinding!!

    private var currentImageUri: Uri? = null
    private val IMAGE_PICK_CODE = 1000
    private lateinit var notificationsHandler: NotificationsHandler

    private val priorities = arrayOf(
        NotificationCompat.PRIORITY_MIN,
        NotificationCompat.PRIORITY_LOW,
        NotificationCompat.PRIORITY_DEFAULT,
        NotificationCompat.PRIORITY_MAX
    )

    private val priorityNames = arrayOf(
        "MIN",
        "LOW",
        "DEFAULT",
        "MAX"
    )

    private var selectedPriority: Int = NotificationCompat.PRIORITY_DEFAULT
    private var isArrowDown: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNotificationsBinding.inflate(inflater, container, false)

        notificationsHandler = NotificationsHandler(requireContext())

        setupPrioritySpinner()
        setupThemeButtons()

        binding.showNotificationButton.setOnClickListener {
            showNotification()
        }

        binding.imageView.setOnClickListener {
            if (currentImageUri == null) {
                pickImageFromGallery()
            } else {
                Toast.makeText(context, getString(R.string.not_empty_image), Toast.LENGTH_SHORT).show()
            }
        }

        binding.deleteImageView.setOnClickListener {
            deleteImage()
        }

        binding.themeSelectorButton.setOnClickListener {
            toggleArrowIcon()
        }

        return binding.root
    }

    private fun loadImageFromDrawable() {
        Glide.with(this)
            .load(R.drawable.cat_pic)
            .apply(RequestOptions().circleCrop())
            .into(binding.imageView)

        binding.imageView.visibility = View.VISIBLE
        binding.deleteImageView.visibility = View.VISIBLE
    }

    private fun setupPrioritySpinner() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorityNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = adapter

        binding.prioritySpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedPriority = priorities[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedPriority = NotificationCompat.PRIORITY_DEFAULT
            }
        })
    }

    private fun setAppTheme(theme: Int) {
        val activity = activity as? MainActivity
        activity?.setAppTheme(theme)
    }

    private fun showNotification() {
        val title = binding.title.text.toString()
        val text = binding.text.text.toString()

        if (title.isEmpty()) {
            Toast.makeText(context, getString(R.string.empty_title_error), Toast.LENGTH_SHORT).show()
            return
        }

        if (text.isEmpty()) {
            Toast.makeText(context, getString(R.string.empty_text_error), Toast.LENGTH_SHORT).show()
            return
        }

        notificationsHandler.showNotification(title, text, selectedPriority)
    }

    private fun loadImage(uri: Uri?) {
        uri?.let {
            Glide.with(this)
                .load(it)
                .apply(RequestOptions().circleCrop())
                .into(binding.imageView)

            binding.imageView.visibility = View.VISIBLE
            binding.deleteImageView.visibility = View.VISIBLE
        } ?: run {
            binding.imageView.setImageURI(null)
            binding.imageView.visibility = View.GONE
            binding.deleteImageView.visibility = View.GONE
        }
    }

    private fun deleteImage() {
        binding.imageView.let { Glide.with(this).clear(it) }
        currentImageUri = null
        binding.deleteImageView.visibility = View.GONE
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE) {
            if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                currentImageUri = data.data
                loadImage(currentImageUri)
            } else {
                Toast.makeText(context, getString(R.string.image_selection_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun toggleArrowIcon() {
        if (isArrowDown) {
            binding.themeSelectorButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_keyboard_double_arrow_up_24, 0)
            showThemeSelector()
        } else {
            binding.themeSelectorButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_keyboard_double_arrow_down_24, 0)
            hideThemeSelector()
        }
        isArrowDown = !isArrowDown
    }

    private fun showThemeSelector() {
        binding.themeRedButton.visibility = View.VISIBLE
        binding.themeGreenButton.visibility = View.VISIBLE
        binding.themeYellowButton.visibility = View.VISIBLE
    }

    private fun hideThemeSelector() {
        binding.themeRedButton.visibility = View.GONE
        binding.themeGreenButton.visibility = View.GONE
        binding.themeYellowButton.visibility = View.GONE
    }

    private fun setupThemeButtons() {
        val themeButtons = mapOf(
            binding.themeRedButton to R.style.RedTheme,
            binding.themeGreenButton to R.style.GreenTheme,
            binding.themeYellowButton to R.style.YellowTheme,
            binding.appTheme to R.style.AppTheme
        )

        for ((button, theme) in themeButtons) {
            button.setOnClickListener {
                setAppTheme(theme)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}
