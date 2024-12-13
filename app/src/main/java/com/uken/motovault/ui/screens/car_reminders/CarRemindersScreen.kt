package com.uken.motovault.ui.screens.car_reminders

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uken.motovault.ui.composables.misc.PageInfoBox
import com.uken.motovault.ui.composables.misc.TopAppBarWithBackButton
import com.uken.motovault.ui.composables.navigationbar.AppNavigationBar
import com.uken.motovault.utilities.IntentUtilities
import com.uken.motovault.utilities.NotificationUtilities
import com.uken.motovault.utilities.PermissionUtilities

@Composable
fun CarRemindersScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val activity = LocalContext.current as? Activity

    // Prompting user for notifications permission when not granted
    LaunchedEffect(Unit) {
        if (!PermissionUtilities.isNotificationPermissionEnabled(context)) {
            PermissionUtilities.requestNotificationPermission(activity?: return@LaunchedEffect)
        }
    }

    Scaffold(
        topBar = { TopAppBarWithBackButton("Reminders", navController) },
        bottomBar = { AppNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            PageInfoBox(
                imageVector = Icons.Default.Alarm,
                contentDescription = "Alarm"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ReminderCard(
                    statusText = "Oil service status: Valid",
                    lastActionText = "Next action: 2024-12-06",
                    onNotifyClick = {
                        NotificationUtilities.showNotification(
                            context,
                            "Oil service status",
                            "It's time to change your engine oil"
                        )
                    },
                    onAddToCalendarClick = {
                        IntentUtilities.addEventToCalendar(
                            context,
                            "Oil service",
                            "It't time to change engine oil",
                            "2024-12-21"
                        )
                    }
                )
                ReminderCard(
                    statusText = "Inspection status: Valid",
                    lastActionText = "Next action: 2024-12-06",
                    onNotifyClick = {
                        NotificationUtilities.showNotification(
                            context,
                            "Inspection status",
                            "Your inspection expired"
                        )
                    },
                    onAddToCalendarClick = {
                        IntentUtilities.addEventToCalendar(
                            context,
                            "Car Inspection",
                            "Another yearly car inspection",
                            "2024-12-21"
                        )
                    }
                )
            }
        }
    }
}