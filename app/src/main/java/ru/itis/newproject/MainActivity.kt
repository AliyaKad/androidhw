package ru.itis.newproject

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.itis.newproject.hw4.NotificationsFragment

class MainActivity : AppCompatActivity() {

    private var currentTheme: Int = R.style.AppTheme
    companion object {
        private var isNotificationShown: Boolean = false
        private const val THEME_KEY = "current_theme"
        private const val LAUNCHED_FROM_NOTIFICATION_KEY = "launched_from_notification"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            currentTheme = savedInstanceState.getInt(THEME_KEY, R.style.AppTheme)
        }

        setTheme(currentTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!isNotificationShown && intent.getBooleanExtra(LAUNCHED_FROM_NOTIFICATION_KEY, false)) {
            showLaunchNotificationToast()
            isNotificationShown = true
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, NotificationsFragment())
                .commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(THEME_KEY, currentTheme)
    }

    fun setAppTheme(theme: Int) {
        currentTheme = theme
        recreate()
    }

    private fun showLaunchNotificationToast() {
        Toast.makeText(this, getString(R.string.launched_from_notification_message), Toast.LENGTH_SHORT).show()
    }
}
