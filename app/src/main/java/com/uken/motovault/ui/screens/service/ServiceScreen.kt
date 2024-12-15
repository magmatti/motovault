package com.uken.motovault.ui.screens.service

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
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
import com.uken.motovault.sign_in.email_sign_in.EmailSignInViewModel
import com.uken.motovault.sign_in.google_sign_in.UserData
import com.uken.motovault.ui.composables.app_navigation_drawer.AppNavigationDrawer
import com.uken.motovault.ui.composables.navigationbar.AppNavigationBar
import com.uken.motovault.ui.composables.top_app_bar.TopAppBar
import com.uken.motovault.viewmodels.NavigationBarViewModel
import com.uken.motovault.viewmodels.ServicesViewModel

@Composable
fun ServiceScreen(
    navController: NavController,
    userData: UserData?,
    onSignOut: () -> Unit,
    emailSignInViewModel: EmailSignInViewModel = viewModel(),
    viewModel: NavigationBarViewModel = viewModel(),
    servicesViewModel: ServicesViewModel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val services by servicesViewModel.services.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val actionIcon = Icons.Default.PictureAsPdf
    val userEmail by emailSignInViewModel.userEmail.observeAsState()

    LaunchedEffect(userEmail) {
        userEmail?.let { email ->
            servicesViewModel.getServices(userEmail!!)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppNavigationDrawer(
                navController,
                userData,
                onSignOut,
                emailSignInViewModel
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    scope = scope,
                    drawerState = drawerState,
                    screenTitle = "Service",
                    actionIcon = actionIcon,
                    onActionClick = {
                        val pdfFile = servicesViewModel.generateServicesPdf(context)
                        if (pdfFile != null) {
                            Toast.makeText(context,
                                "PDF saved to ${pdfFile.absolutePath}",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "Failed to generate PDF.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                )
            },
            bottomBar = { AppNavigationBar(navController, viewModel) },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { showDialog = true },
                    icon = { Icon(Icons.Filled.Handyman, "Add Icon") },
                    text = { Text(text = "New service") },
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                        end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                        bottom = innerPadding.calculateBottomPadding() + 80.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (services.isEmpty()) {
                    Text("No services available")
                } else {
                    LazyColumn {
                        items(services) { service ->
                            ServiceItemCard(
                                service = service,
                                onDelete = { id ->
                                    servicesViewModel.deleteService(id, userEmail!!)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddServiceDialog(
            userEmail!!,
            onDismiss = { showDialog = false },
            onAddService = { service ->
                servicesViewModel.addService(service)
                showDialog = false
            }
        )
    }
}