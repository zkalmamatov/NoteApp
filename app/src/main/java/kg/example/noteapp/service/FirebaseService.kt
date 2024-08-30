package kg.example.noteapp.service

import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kg.example.noteapp.R
import kg.example.noteapp.ui.activity.MainActivity

class FirebaseService : FirebaseMessagingService() {

    companion object {
        const val CHANNEL_ID = "notification_channel"
        const val CHANNEL_NAME = "Notification_Channel"
        const val NOTIFICATION_ID = 0
    }

    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.let { notification ->
            val title = notification.title
            val body = notification.body
            showNotification(title, body)
        }
    }

    private fun showNotification(title: String?, body: String?) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
        PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        val notificationLayout = getNotificationLayout(title, body)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)

        createNotificationChannel()
        with(NotificationManagerCompat.from(this)){
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun getNotificationLayout(title: String?, body: String?): Any {

    }

    private fun createNotificationChannel() {
        TODO("Not yet implemented")
    }

}