package ru.itis.newproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.itis.newproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainContainerId: Int = R.id.main_fragment_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(mainContainerId, Fragment1()).commit()
    }

    private companion object {
        private const val HEADER_KEY = "PAGE_HEADER"
    }
}