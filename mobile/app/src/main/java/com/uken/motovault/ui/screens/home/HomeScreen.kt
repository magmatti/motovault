package com.uken.motovault.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.uken.motovault.datastores.UserEmailDataStore
import com.uken.motovault.sign_in.email_sign_in.EmailSignInViewModel
import com.uken.motovault.sign_in.google_sign_in.UserData
import com.uken.motovault.ui.Routes
import com.uken.motovault.ui.composables.app_navigation_drawer.AppNavigationDrawer
import com.uken.motovault.ui.composables.navigationbar.AppNavigationBar
import com.uken.motovault.ui.composables.top_app_bar.TopAppBar
import com.uken.motovault.viewmodels.HomeViewModel
import com.uken.motovault.viewmodels.NavigationBarViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    userData: UserData?,
    onSignOut: () -> Unit,
    emailSignInViewModel: EmailSignInViewModel = viewModel(),
    viewModel: NavigationBarViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel(),
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    val vehicleList by homeViewModel.vehicles.collectAsState()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val userEmail by emailSignInViewModel.userEmail.observeAsState()
    val userEmailDataStore = UserEmailDataStore.getInstance(context)

    LaunchedEffect(userEmail) {
        userEmail?.let { email ->
            homeViewModel.fetchVehicles(email)
            userEmailDataStore.saveUserEmail(email)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { AppNavigationDrawer(
                navController,
                userData,
                onSignOut,
                emailSignInViewModel
            )
        }
    ) {
        Scaffold(
            topBar = { TopAppBar(scope, drawerState, "Home") },
            bottomBar = { AppNavigationBar(navController, viewModel) },
            floatingActionButton = {
                if (vehicleList.isEmpty()) {
                    ExtendedFloatingActionButton(
                        onClick = { showDialog = true },
                        icon = { Icon(Icons.Filled.Add, "Add Icon") },
                        text = { Text(text = "Add Vehicle") },
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                        end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                        bottom = innerPadding.calculateBottomPadding() + 80.dp
                    )
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(vehicleList) { vehicle ->
                        VehicleItem(
                            onDetailsClick = {
                                navController.navigate(
                                    "VehicleInfoScreen/${vehicle.id}/${vehicle.vin}"
                                )
                            },
                            onRemindersClick = {
                                navController.navigate(Routes.CAR_REMINDERS_SCREEN)
                            },
                            onCarDeleteClick = {
                                homeViewModel.deleteVehicle(vehicle.id!!)
                            },
                            vehicleMake = vehicle.make.toString(),
                            vehicleModel = vehicle.model.toString()
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddVehicleDialog(
            onDismiss = { showDialog = false },
            onAddVehicle = { vehicle ->
                homeViewModel.addVehicle(vehicle)
                showDialog = false
            },
            email = userEmail!!
        )
    }
}