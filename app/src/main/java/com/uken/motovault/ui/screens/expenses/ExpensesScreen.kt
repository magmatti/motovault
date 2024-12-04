package com.uken.motovault.ui.screens.expenses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.uken.motovault.sign_in.email_sign_in.EmailSignInViewModel
import com.uken.motovault.sign_in.google_sign_in.UserData
import com.uken.motovault.ui.composables.app_navigation_drawer.AppNavigationDrawer
import com.uken.motovault.ui.composables.navigationbar.AppNavigationBar
import com.uken.motovault.ui.composables.top_app_bar.TopAppBar
import com.uken.motovault.utilities.DocumentGeneration
import com.uken.motovault.viewmodels.ExpensesViewModel
import com.uken.motovault.viewmodels.NavigationBarViewModel

@Composable
fun ExpensesScreen(
    navController: NavController,
    userData: UserData?,
    onSignOut: () -> Unit,
    emailSignInViewModel: EmailSignInViewModel = viewModel(),
    viewModel: NavigationBarViewModel = viewModel(),
    expensesViewModel: ExpensesViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val expenses by expensesViewModel.expenses.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    val actionIcon = Icons.Default.Dataset

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
                    screenTitle = "Expenses",
                    actionIcon = actionIcon,
                    onActionClick = { DocumentGeneration.testOnActionClick() }
                )
            },
            bottomBar = { AppNavigationBar(navController, viewModel) },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { showDialog = true },
                    icon = { Icon(Icons.Filled.Money, "Add Icon") },
                    text = { Text(text = "New expense") },
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (expenses.isEmpty()) {
                    Text("No expenses available")
                } else {
                    LazyColumn {
                        items(expenses) { expense ->
                            ExpenseItemCard(
                                expense = expense,
                                onDelete = { id ->
                                    expensesViewModel.deleteExpense(id)
                                }
                            )
                        }
                    }
                }
            }

            if (showDialog) {
                AddExpenseDialog(
                    onDismiss = { showDialog = false },
                    onAddExpense = { expense ->
                        expensesViewModel.addExpense(expense)
                        showDialog = false
                    }
                )
            }
        }
    }
}