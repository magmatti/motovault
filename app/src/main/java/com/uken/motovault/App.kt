package com.uken.motovault

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.uken.motovault.workmanager.ServiceStatusWorker
import java.util.concurrent.TimeUnit

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        val serviceStatusWorkRequest = PeriodicWorkRequestBuilder<ServiceStatusWorker>(15, TimeUnit.MINUTES)
            .setInitialDelay(30, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(serviceStatusWorkRequest)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "carServiceChannel",
            "Car service notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}