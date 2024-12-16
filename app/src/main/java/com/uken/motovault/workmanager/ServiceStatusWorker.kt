package com.uken.motovault.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.uken.motovault.datastores.NotificationsDataStore
import com.uken.motovault.utilities.NotificationUtilities
import com.uken.motovault.utilities.RemindersScreenDateUtilities
import com.uken.motovault.viewmodels.ServicesViewModel

class ServiceStatusWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val dataStore = NotificationsDataStore.getInstance(applicationContext)
        val notificationsEnabled = dataStore.getNotificationsEnabled()

        if (!notificationsEnabled) {
            return Result.success()
        }

        val servicesViewModel = ServicesViewModel()
        val lastOilChangeDate = servicesViewModel.lastOilChangeDate.value
        val lastInspectionDate = servicesViewModel.lastInspectionDate.value

        Log.d("doWork", "doWork: $lastOilChangeDate, $lastInspectionDate")

        val oilServiceStatus = RemindersScreenDateUtilities
            .getOilChangeStatus(lastOilChangeDate, "Every year")
        val inspectionServiceStatus = RemindersScreenDateUtilities
            .getServiceStatus(lastInspectionDate)

        Log.d("doWork", "doWork: $oilServiceStatus, $inspectionServiceStatus")

        if (oilServiceStatus == "Expired") {
            NotificationUtilities.showNotification(
                applicationContext,
                "Oil Service Status",
                "It's time to change your engine oil"
            )
        }

        if (inspectionServiceStatus == "Expired") {
            NotificationUtilities.showNotification(
                applicationContext,
                "Inspection Status",
                "Your car inspection has expired"
            )
        }

        return Result.success()
    }
}