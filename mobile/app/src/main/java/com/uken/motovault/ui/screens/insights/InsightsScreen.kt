package com.uken.motovault.ui.screens.insights

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.uken.motovault.sign_in.email_sign_in.EmailSignInViewModel
import com.uken.motovault.sign_in.google_sign_in.UserData
import com.uken.motovault.ui.composables.app_navigation_drawer.AppNavigationDrawer
import com.uken.motovault.ui.composables.navigationbar.AppNavigationBar
import com.uken.motovault.ui.composables.top_app_bar.TopAppBar
import com.uken.motovault.viewmodels.InsightsViewModel
import com.uken.motovault.viewmodels.NavigationBarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightsScreen(
    navController: NavController,
    userData: UserData?,
    onSignOut: () -> Unit,
    emailSignInViewModel: EmailSignInViewModel = viewModel(),
    viewModel: NavigationBarViewModel = viewModel(),
    insightsViewModel: InsightsViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val sheetState = rememberModalBottomSheetState()
    val showSheet = remember { mutableStateOf(false) }
    val yearlyExpenses by insightsViewModel.yearlyExpenses.collectAsState()
    val monthlyExpenses by insightsViewModel.monthlyExpenses.collectAsState()
    var selectedMonth by remember { mutableStateOf("January") }
    var selectedMonthNumber by remember { mutableIntStateOf(1) }
    val userEmail by emailSignInViewModel.userEmail.observeAsState()

    LaunchedEffect(userEmail) {
        userEmail?.let { email ->
            insightsViewModel.getYearlyExpenses("2025", email)
            insightsViewModel.getMonthlyExpenses("2025", "1", email)
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
            topBar = { TopAppBar(scope, drawerState, "Insights") },
            bottomBar = { AppNavigationBar(navController, viewModel) }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (yearlyExpenses.size < 2) {
                    Text("At least two expenses of different type needed")
                } else {
                    ExpensePieChart("Yearly spending", yearlyExpenses)
                }

                if (monthlyExpenses.size < 2) {
                    Text("At least two expenses of different type needed")
                } else {
                    ExpensePieChart("Monthly spending", monthlyExpenses)
                }

                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { showSheet.value = true }
                ) {
                    Text(
                        text = "Selected Month: $selectedMonth",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }

    if (showSheet.value) {
        MonthSelectorSheet(
            sheetState = sheetState,
            showSheet = showSheet
        ) { monthName, monthNumber ->
            selectedMonth = monthName
            selectedMonthNumber = monthNumber
            userEmail?.let { email ->
                insightsViewModel.getMonthlyExpenses("2025", monthNumber.toString(), email)
            }
        }
    }
}