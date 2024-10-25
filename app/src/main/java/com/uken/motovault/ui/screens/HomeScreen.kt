package com.uken.motovault.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uken.motovault.ui.composables.navigationbar.NavigationBar
import com.uken.motovault.ui.composables.navigationbar.NavigationBarItem
import com.uken.motovault.ui.Routes

@Composable
fun HomeScreen(context: Context, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Home",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(Alignment.Start)
        )

        // To be removed later
        NavigationBarItem(
            icon = Icons.AutoMirrored.Filled.Logout,
            label = "Logout",
            onClick = { onLogoutButtonClick(Routes.LOGIN_SCREEN, navController) }
        )

        NavigationBar(navController = navController)
    }
}

fun onLogoutButtonClick(route: String, navController: NavController) {
    navController.navigate(route)
}
