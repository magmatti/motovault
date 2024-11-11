package com.uken.motovault.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.uken.motovault.ui.composables.navigationbar.AppNavigationBar
import com.uken.motovault.ui.composables.navigationbar.NavigationBarViewModel

@Composable
fun ExpensesScreen(
    navController: NavController,
    viewModel: NavigationBarViewModel = viewModel()
) {
    Scaffold(
        bottomBar = { AppNavigationBar(navController, viewModel) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Expenses",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.Start),
            )
        }
    }
}