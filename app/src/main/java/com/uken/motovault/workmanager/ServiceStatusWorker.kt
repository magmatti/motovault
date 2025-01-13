package com.uken.motovault.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.uken.motovault.datastores.NotificationsDataStore
import com.uken.motovault.datastores.OilChangeIntervalDataStore
import com.uken.motovault.datastores.UserEmailDataStore
import com.uken.motovault.utilities.NotificationUtilities
import com.uken.motovault.utilities.RemindersScreenDateUtilities
import com.uken.motovault.viewmodels.ServicesViewModel
import kotlinx.coroutines.flow.StateFlow

class ServiceStatusWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    val servicesViewModel = ServicesViewModel()
    val notificationsDataStore = NotificationsDataStore.getInstance(context)
    val userEmailDataStore = UserEmailDataStore.getInstance(context)
    val oilChangeIntervalDataStore = OilChangeIntervalDataStore.getInstance(context)

    companion object {
        const val TAG = "ServiceStatusWorker"
    }

    override suspend fun doWork(): Result {
        val notificationsEnabled = notificationsDataStore.getNotificationsEnabled()
        val oilChangeInterval = oilChangeIntervalDataStore.getLastOilChangeInterval()
        val userEmail = userEmailDataStore.getUserEmail()

        servicesViewModel.getServices(userEmail)

        val lastOilChangeDate = waitForValue(servicesViewModel.lastOilChangeDate)
        val lastInspectionDate = waitForValue(servicesViewModel.lastInspectionDate)

        val oilServiceStatus = RemindersScreenDateUtilities
            .getOilChangeStatus(lastOilChangeDate, oilChangeInterval)
        val inspectionServiceStatus = RemindersScreenDateUtilities
            .getServiceStatus(lastInspectionDate)

        if (!notificationsEnabled) {
            Log.d(TAG, "Notifications are disabled")
            return Result.failure()
        }

        Log.d(TAG, "doWork: $userEmail")
        Log.d(TAG, "doWork: $lastOilChangeDate, $lastInspectionDate")
        Log.d(TAG, "doWork: Oil - $oilServiceStatus, Inspection - $inspectionServiceStatus")

        triggerOilServiceNotificationIfExpired(oilServiceStatus)
        triggerInspectionServiceNotificationIfExpired(inspectionServiceStatus)

        return Result.success()
    }

    private fun triggerOilServiceNotificationIfExpired(oilServiceStatus: String) {
        if (oilServiceStatus == "Expired") {
            NotificationUtilities.showNotification(
                applicationContext,
                "Oil Service Status",
                "It's time to change your engine oil"
            )
        }
    }

    private fun triggerInspectionServiceNotificationIfExpired(inspectionServiceStatus: String) {
        if (inspectionServiceStatus == "Expired") {
            NotificationUtilities.showNotification(
                applicationContext,
                "Inspection Status",
                "Your car inspection has expired"
            )
        }
    }

    private suspend fun <T> waitForValue(stateFlow: StateFlow<T?>, timeout: Long = 7000): T? {
        val startTime = System.currentTimeMillis()
        while (stateFlow.value == null && System.currentTimeMillis() - startTime < timeout) {
            kotlinx.coroutines.delay(100)
        }
        return stateFlow.value
    }
}