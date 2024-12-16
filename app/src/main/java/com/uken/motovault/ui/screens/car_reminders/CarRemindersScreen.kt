package com.uken.motovault.ui.screens.car_reminders

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.uken.motovault.datastores.OilChangeIntervalDataStore
import com.uken.motovault.sign_in.email_sign_in.EmailSignInViewModel
import com.uken.motovault.ui.composables.misc.PageInfoBox
import com.uken.motovault.ui.composables.misc.TopAppBarWithBackButton
import com.uken.motovault.ui.composables.navigationbar.AppNavigationBar
import com.uken.motovault.utilities.IntentUtilities
import com.uken.motovault.utilities.NotificationUtilities
import com.uken.motovault.utilities.RemindersScreenDateUtilities
import com.uken.motovault.viewmodels.ServicesViewModel

@Composable
fun CarRemindersScreen(
    navController: NavController,
    servicesViewModel: ServicesViewModel = viewModel(),
    emailSignInViewModel: EmailSignInViewModel = viewModel(),
    oilChangeIntervalDataStore: OilChangeIntervalDataStore =
        OilChangeIntervalDataStore.getInstance(LocalContext.current)
) {
    val context = LocalContext.current
    val userEmail by emailSignInViewModel.userEmail.observeAsState()

    val lastOilChangeDate by servicesViewModel.lastOilChangeDate.collectAsState()
    val lastInspectionDate by servicesViewModel.lastInspectionDate.collectAsState()
    val nextOilChangeDate = lastOilChangeDate?.let {
        RemindersScreenDateUtilities.calculateNextActionDate(it)
    } ?: "Unknown"
    val nextInspectionDate = lastInspectionDate?.let {
        RemindersScreenDateUtilities.calculateNextActionDate(it)
    } ?: "Unknown"

    val oilChangeInterval = remember { mutableStateOf("Every year") }

    LaunchedEffect(Unit) {
        oilChangeInterval.value = oilChangeIntervalDataStore.getLastOilChangeInterval()
    }

    LaunchedEffect(userEmail) {
        userEmail?.let { email ->
            servicesViewModel.getServices(email)
        }
    }

    val oilServiceStatus = RemindersScreenDateUtilities
        .getOilChangeStatus(lastOilChangeDate, oilChangeInterval.value)
    val inspectionServiceStatus = RemindersScreenDateUtilities.getServiceStatus(lastInspectionDate)

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
                    statusText = "Oil service: $oilServiceStatus",
                    lastActionText = "Last service: ${lastOilChangeDate ?: "Unknown"}",
                    onNotifyClick = {
                        NotificationUtilities.showNotification(
                            context,
                            "Oil service status",
                            "It's time to change your engine oil"
                        )
                    },
                    onAddToCalendarClick = {
                        if (nextOilChangeDate != "Unknown") {
                            IntentUtilities.addEventToCalendar(
                                context,
                                "Oil service",
                                "It's time to change engine oil",
                                nextOilChangeDate
                            )
                        }
                    }
                )
                ReminderCard(
                    statusText = "Inspection: $inspectionServiceStatus",
                    lastActionText = "Last inspection: ${lastInspectionDate ?: "Unknown"}",
                    onNotifyClick = {
                        NotificationUtilities.showNotification(
                            context,
                            "Inspection status",
                            "Your inspection expired"
                        )
                    },
                    onAddToCalendarClick = {
                        if (nextInspectionDate != "Unknown") {
                            IntentUtilities.addEventToCalendar(
                                context,
                                "Car Inspection",
                                "Another yearly car inspection",
                                nextInspectionDate
                            )
                        }
                    }
                )
            }
        }
    }
}