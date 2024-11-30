package com.uken.motovault.ui.screens.car_reminders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uken.motovault.ui.composables.misc.TopAppBarWithBackButton
import com.uken.motovault.ui.composables.navigationbar.AppNavigationBar

@Composable
fun CarRemindersScreen(
    navController: NavController
) {
    Scaffold(
        topBar = { TopAppBarWithBackButton("Reminders", navController) },
        bottomBar = { AppNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Alarm,
                    contentDescription = "Alarm",
                    modifier = Modifier.size(128.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ReminderCard(
                    statusText = "Oil service status: Valid",
                    lastActionText = "Last action: 2024-21-11",
                    onNotifyClick = { /* Handle notification */ }
                )
                ReminderCard(
                    statusText = "Inspection status: Valid",
                    lastActionText = "Last action: 2024-21-11",
                    onNotifyClick = { /* Handle notification */ }
                )
            }
        }
    }
}