package ru.itis.newproject.hw4

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import ru.itis.newproject.MainActivity
import ru.itis.newproject.R

class NotificationsHandler(private val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = listOf(
                NotificationChannel(
                    CHANNEL_LOW_PRIORITY,
                    "Low Priority Channel",
                    NotificationManager.IMPORTANCE_LOW
                ).apply {
                    description = "Channel for low priority notifications"
                },
                NotificationChannel(
                    CHANNEL_DEFAULT,
                    "Default Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Channel for default notifications"
                },
                NotificationChannel(
                    CHANNEL_MAX_PRIORITY,
                    "Max Priority Channel",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Channel for high priority notifications"
                }
            )

            channels.forEach { channel ->
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    fun showNotification(title: String, text: String, selectedPriority: Int) {
        val notificationId = 1

        val channelId = when (selectedPriority) {
            NotificationCompat.PRIORITY_MIN -> CHANNEL_LOW_PRIORITY
            NotificationCompat.PRIORITY_LOW -> CHANNEL_LOW_PRIORITY
            NotificationCompat.PRIORITY_DEFAULT -> CHANNEL_DEFAULT
            NotificationCompat.PRIORITY_MAX -> CHANNEL_MAX_PRIORITY
            else -> CHANNEL_DEFAULT
        }

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.baseline_favorite_24)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(selectedPriority)
            .setAutoCancel(true)

        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("launched_from_notification", true)
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        notificationBuilder.setContentIntent(pendingIntent)

        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    companion object {
        private const val CHANNEL_LOW_PRIORITY = "low_priority"
        private const val CHANNEL_DEFAULT = "default"
        private const val CHANNEL_MAX_PRIORITY = "max_priority"
    }
}
