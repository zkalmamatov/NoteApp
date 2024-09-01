package kg.example.noteapp.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kg.example.noteapp.R
import kg.example.noteapp.ui.activity.MainActivity
import kg.example.noteapp.utils.PreferenceHelper

class FirebaseService : FirebaseMessagingService() {

    companion object {
        const val CHANNEL_ID = "notification_channel"
        const val CHANNEL_NAME = "Notification_Channel"
        const val NOTIFICATION_ID = 0
    }

    private val sharedPreference: PreferenceHelper by lazy {
        PreferenceHelper().apply {
            unit(this@FirebaseService)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.let { notification ->
            val title = notification.title
            val body = notification.body

            if (areNotificationsEnabled()) {
                showNotification(title, body)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun showNotification(title: String?, body: String?) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationLayout = getNotificationLayout(title, body)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)

        createNotificationChannel()
        NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, builder.build())
    }

    private fun getNotificationLayout(title: String?, body: String?): RemoteViews {
        val remoteViews = RemoteViews(packageName, R.layout.item_message)
        remoteViews.setTextViewText(R.id.txt_title_m, title ?: "No Title")
        remoteViews.setTextViewText(R.id.txt_body, body ?: "No Body")
        remoteViews.setImageViewResource(R.id.img_logo, R.drawable.ic_notification)
        return remoteViews
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Channel description"
                }
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun areNotificationsEnabled(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        }else {
            sharedPreference.notificationsEnabled
        }
    }

}