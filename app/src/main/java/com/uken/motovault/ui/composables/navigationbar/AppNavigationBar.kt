package com.uken.motovault.ui.composables.navigationbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.uken.motovault.ui.Routes

@Composable
fun AppNavigationBar(
    navController: NavController,
    viewModel: NavigationBarViewModel = viewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    LaunchedEffect(navBackStackEntry) {
        viewModel.updateSelectedIndexByRoute(navBackStackEntry?.destination?.route ?: "")
    }

    val items = listOf(
        NavigationBarItem(
            label = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = Routes.HOME_SCREEN
        ),
        NavigationBarItem(
            label = "Expenses",
            selectedIcon = Icons.Filled.AttachMoney,
            unselectedIcon = Icons.Outlined.AttachMoney,
            route = Routes.EXPENSES_SCREEN
        ),
        NavigationBarItem(
            label = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            route = Routes.SETTINGS_SCREEN
        ),
        NavigationBarItem(
            label = "Account",
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle,
            route = Routes.ACCOUNT_SCREEN
        ),
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = viewModel.selectedItemIndex == index,
                onClick = {
                    viewModel.onItemSelected(index)
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        imageVector = if (viewModel.selectedItemIndex == index) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(text = item.label)
                }
            )
        }
    }
}