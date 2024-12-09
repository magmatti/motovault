package com.uken.motovault.ui.screens.insights

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hd.charts.PieChartView
import com.hd.charts.common.model.ChartDataSet
import com.hd.charts.style.PieChartDefaults
import com.uken.motovault.models.charts.ChartExpense
import com.uken.motovault.sign_in.email_sign_in.EmailSignInViewModel
import com.uken.motovault.sign_in.google_sign_in.UserData
import com.uken.motovault.ui.composables.app_navigation_drawer.AppNavigationDrawer
import com.uken.motovault.ui.composables.navigationbar.AppNavigationBar
import com.uken.motovault.ui.composables.top_app_bar.TopAppBar
import com.uken.motovault.viewmodels.NavigationBarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightsScreen(
    navController: NavController,
    userData: UserData?,
    onSignOut: () -> Unit,
    emailSignInViewModel: EmailSignInViewModel = viewModel(),
    viewModel: NavigationBarViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val sheetState = rememberModalBottomSheetState()
    val showSheet = remember { mutableStateOf(false) }

    // Sample yearly chart data (to be fetched from server later)
    val yearlyChartData = listOf(
        ChartExpense("Detailing", 1000f),
        ChartExpense("Speeding Tickets", 200f),
        ChartExpense("Insurance", 1340f),
        ChartExpense("Petrol", 4634f)
    )

    // Sample monthly chart data (to be fetched from server later)
    val monthlyDataMap = mapOf(
        "January" to listOf(ChartExpense("Petrol", 500f), ChartExpense("Insurance", 100f)),
        "February" to listOf(ChartExpense("Detailing", 400f), ChartExpense("Speeding Ticket", 50f)),
        "March" to listOf(ChartExpense("Petrol", 300f), ChartExpense("Detailing", 200f))
    )

    var selectedMonth by remember { mutableStateOf("January") }
    val monthlyChartData = remember(selectedMonth) {
        monthlyDataMap[selectedMonth] ?: emptyList()
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
                ExpensePieChart("Yearly spending by expense type", yearlyChartData)
                ExpensePieChart("Monthly spending by expense type", monthlyChartData)

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
        ModalBottomSheet(
            onDismissRequest = { showSheet.value = false },
            sheetState = sheetState
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                monthlyDataMap.keys.forEach { month ->
                    Text(
                        text = month,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                selectedMonth = month
                                showSheet.value = false
                            }
                    )
                }
            }
        }
    }

}

@Composable
private fun ExpensePieChart(title: String, items: List<ChartExpense>) {
    val values = items.map { it.amount }
    val labels = items.map { "${it.expenseType}: ${it.amount} PLN" }
    val pieColors = generateColors(items.size)

    val dataSet = ChartDataSet(
        items = values,
        title = title,
        postfix = " PLN"
    )

    val style = PieChartDefaults.style(
        borderColor = Color.Black,
        donutPercentage = 40f,
        borderWidth = 10f,
        pieColors = pieColors,
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        PieChartView(
            dataSet = dataSet,
            style = style
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            labels.forEachIndexed { index, label ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(4.dp)
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                            .background(pieColors[index % pieColors.size])
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = label, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

private fun generateColors(size: Int): List<Color> {
    val baseColors = listOf(
        Color(0xFF6A5ACD),
        Color(0xFF4682B4),
        Color(0xFF87CEEB),
        Color(0xFFB0C4DE),
        Color(0xFF778899)
    )

    return List(size) { index ->
        baseColors[index % baseColors.size]
    }
}