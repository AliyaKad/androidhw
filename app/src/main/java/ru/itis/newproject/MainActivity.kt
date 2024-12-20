package ru.itis.newproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.itis.newproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, MainFragment())
                .commit()
        }
    }
}