package mf.patomessenger.presentation.base

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.RemoteMessage
import mf.patomessenger.R
import mf.patomessenger.configurations.CHANNEL_ID
import java.util.*


fun createNotification(title: String, body: String, context: Context): NotificationCompat.Builder =
    NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_baseline_chat_24)
        .setContentTitle(title)
        .setContentText(body)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

fun RemoteMessage.showNotification(context: Context) =
    NotificationManagerCompat.from(context).notify(
        buildNotificationId(),
        createNotification(
            notification?.title.orEmpty(),
            notification?.body.orEmpty(),
            context
        ).build()
    )

private fun buildNotificationId(): Int =
    Calendar.getInstance().timeInMillis.toInt()