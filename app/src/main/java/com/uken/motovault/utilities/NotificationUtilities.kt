package com.uken.motovault.utilities

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.uken.motovault.R

object NotificationUtilities {

    fun showNotification(
        context: Context,
        title: String,
        content: String
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, "carServiceChannel")
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(1, notification)
    }
}