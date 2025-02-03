package ru.itis.newproject

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import ru.itis.newproject.hw5.CoroutineFragment

class MainActivity : AppCompatActivity() {

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                resetDenialCount()
                addCoroutineFragment()
            } else {
                handlePermissionDenied()
            }
        }

    private var denialCount: Int = 0
    private val sharedPreferences by lazy {
        getSharedPreferences("app_preferences", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        denialCount = sharedPreferences.getInt("denial_count", 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!hasNotificationPermission()) {
                requestNotificationPermission()
            } else {
                addCoroutineFragment()
            }
        } else {
            addCoroutineFragment()
        }
    }

    private fun hasNotificationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestNotificationPermission() {
        if (!isFinishing && !isDestroyed) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun handlePermissionDenied() {
        denialCount++
        sharedPreferences.edit().putInt("denial_count", denialCount).apply()

        if (denialCount > 2) {
            showPermissionDeniedDialog()
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                showPermissionRationaleDialog()
            }
        }
    }

    private fun addCoroutineFragment() {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, CoroutineFragment())
        }
    }

    private fun showPermissionRationaleDialog() {
        if (!isFinishing && !isDestroyed) {
            AlertDialog.Builder(this)
                .setTitle(R.string.permission_rationale_title)
                .setMessage(R.string.permission_rationale_message)
                .setPositiveButton(R.string.provide_permission) { _, _ ->
                    requestNotificationPermission()
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
        }
    }

    private fun showPermissionDeniedDialog() {
        if (!isFinishing && !isDestroyed) {
            AlertDialog.Builder(this)
                .setTitle(R.string.permission_denied_title)
                .setMessage(R.string.permission_denied_message)
                .setPositiveButton(R.string.settings) { _, _ ->
                    openAppSettings()
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
        }
    }

    private fun openAppSettings() {
        val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.parse("package:$packageName")
        }
        startActivity(intent)
    }

    private fun resetDenialCount() {
        denialCount = 0
        sharedPreferences.edit().putInt("denial_count", denialCount).apply()
    }
}
