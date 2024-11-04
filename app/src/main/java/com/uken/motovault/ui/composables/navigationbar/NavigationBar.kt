package com.uken.motovault.ui.composables.navigationbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uken.motovault.ui.Routes

@Composable
fun NavigationBar(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 1.dp),
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationBarItem(
                icon = Icons.Filled.Home,
                label = "Home",
                onClick = { onNavBarItemClick(Routes.HOME_SCREEN, navController) }
            )
            NavigationBarItem(
                icon = Icons.Filled.AttachMoney,
                label = "Expenses",
                onClick = { onNavBarItemClick(Routes.EXPENSES_SCREEN, navController) }
            )
            NavigationBarItem(
                icon = Icons.Filled.Settings,
                label = "Settings",
                onClick = { onNavBarItemClick(Routes.SETTINGS_SCREEN, navController) }
            )
            NavigationBarItem(
                icon = Icons.Filled.AccountCircle,
                label = "Account",
                onClick = { onNavBarItemClick(Routes.ACCOUNT_SCREEN, navController) }
            )
        }
    }
}

fun onNavBarItemClick(route: String, navController: NavController) {
    navController.navigate(route)
}
