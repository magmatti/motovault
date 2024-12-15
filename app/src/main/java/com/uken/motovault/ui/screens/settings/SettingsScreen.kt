package com.uken.motovault.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.uken.motovault.datastores.NotificationsDataStore
import com.uken.motovault.datastores.OilChangeIntervalDataStore
import com.uken.motovault.sign_in.email_sign_in.EmailSignInViewModel
import com.uken.motovault.sign_in.google_sign_in.UserData
import com.uken.motovault.ui.Routes
import com.uken.motovault.ui.composables.app_navigation_drawer.AppNavigationDrawer
import com.uken.motovault.ui.composables.navigationbar.AppNavigationBar
import com.uken.motovault.ui.composables.top_app_bar.TopAppBar
import com.uken.motovault.utilities.IntentUtilities
import com.uken.motovault.viewmodels.NavigationBarViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    userData: UserData?,
    onSignOut: () -> Unit,
    emailSignInViewModel: EmailSignInViewModel = viewModel(),
    viewModel: NavigationBarViewModel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val oilChangeIntervalDataStore = remember { OilChangeIntervalDataStore(context) }
    val notificationsDataStore = remember { NotificationsDataStore(context) }
    val oilChangeInterval by oilChangeIntervalDataStore.oilChangeInterval
        .collectAsState(initial = "Every year")
    val notificationsEnabled by notificationsDataStore.areNotificationsEnabled
        .collectAsState(initial = true)
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val bottomSheetOptions = listOf("Every year", "6 months", "3 months")

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
            topBar = { TopAppBar(scope, drawerState, "Settings") },
            bottomBar = { AppNavigationBar(navController, viewModel) }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(top = 8.dp))

                SettingsSectionTitle("Notifications")

                NotificationSettingRow(
                    notificationsEnabled = notificationsEnabled,
                    onToggle = {
                        scope.launch {
                            notificationsDataStore.saveOilChangeInterval(!notificationsEnabled)
                        }
                    }
                )

                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

                OilChangeIntervalRow(
                    oilChangeInterval = oilChangeInterval,
                    onClick = { showBottomSheet = true }
                )

                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

                SettingsSectionTitle("App")

                SettingsNavigationButton(
                    "About app",
                    onClick = { navController.navigate(Routes.ABOUT_APP_SCREEN) }
                )

                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

                SettingsSectionTitle("Issues", color = Color.Red)
                SettingsNavigationButton(
                    "Report bug",
                    onClick = {
                        IntentUtilities.startSupportEmailIntent(context)
                    }
                )
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState
                ) {
                    SettingsModalBottomSheetList(
                        options = bottomSheetOptions,
                        selectedOption = oilChangeInterval,
                        onOptionSelected = { selectedOption ->
                            scope.launch {
                                oilChangeIntervalDataStore.saveOilChangeInterval(
                                    selectedOption
                                )
                            }
                            showBottomSheet = false
                        }
                    )
                }
            }
        }
    }
}