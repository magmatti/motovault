package com.uken.motovault.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.uken.motovault.presentation.vehicle_info.VehicleInfo
import com.uken.motovault.ui.Routes
import com.uken.motovault.ui.composables.home_screen.VehicleItem
import com.uken.motovault.ui.composables.navigationbar.AppNavigationBar
import com.uken.motovault.ui.composables.navigationbar.NavigationBarViewModel
import com.uken.motovault.ui.composables.top_app_bar.TopAppBar

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: NavigationBarViewModel = viewModel()
) {
    var showDialog by remember { mutableStateOf(false) }
    var vehicleName by remember { mutableStateOf("") }
    var vinNumber by remember { mutableStateOf("") }
    var vehicleList by remember { mutableStateOf(listOf<VehicleInfo>()) }
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            "Menu",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.headlineMedium
                        )
                        HorizontalDivider()
                        NavigationDrawerItem(
                            icon = {Icon(Icons.Filled.Settings, contentDescription = "Settings")},
                            label = { Text(text = "Settings") },
                            selected = false,
                            onClick = { navController.navigate(Routes.SETTINGS_SCREEN) }
                        )
                        NavigationDrawerItem(
                            icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Account") },
                            label = { Text(text = "Account") },
                            selected = false,
                            onClick = { navController.navigate(Routes.ACCOUNT_SCREEN) }
                        )
                    }
                    Column {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Logged in as:",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "John Doe", // Replace with actual user name
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            TextButton(
                                onClick = { /* Logout action */ },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = "Log Out", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = { TopAppBar(scope, drawerState, "Home") },
            bottomBar = { AppNavigationBar(navController, viewModel) },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { showDialog = true },
                    icon = { Icon(Icons.Filled.Add, "Add Icon") },
                    text = { Text(text = "Add Vehicle") },
                )
            },
            floatingActionButtonPosition = FabPosition.End
        ) { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(vehicleList) { vehicle ->
                        VehicleItem(
                            vehicle,
                            onDetailsClick = {
                                navController.navigate(Routes.VEHICLE_INFO_SCREEN)
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(text = "Add Vehicle")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = vehicleName,
                        onValueChange = { vehicleName = it },
                        label = { Text("Vehicle Name") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = vinNumber,
                        onValueChange = { vinNumber = it },
                        label = { Text("VIN Number") }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (vehicleName.isNotBlank() && vinNumber.isNotBlank()) {
                            vehicleList = vehicleList + VehicleInfo(
                                name = vehicleName,
                                vin = vinNumber
                            )
                            vehicleName = ""
                            vinNumber = ""
                        }
                        showDialog = false
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}