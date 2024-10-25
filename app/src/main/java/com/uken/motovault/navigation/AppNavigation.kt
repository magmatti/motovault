package com.uken.motovault.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uken.motovault.screens.ExpensesScreen
import com.uken.motovault.screens.HomeScreen
import com.uken.motovault.screens.LoginScreen
import com.uken.motovault.screens.SettingsScreen

@Composable
fun AppNavigation(context: Context) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN_SCREEN,
        builder = {
            composable(Routes.LOGIN_SCREEN) {
                LoginScreen(context, navController)
            }
            composable(Routes.HOME_SCREEN) {
                HomeScreen(context, navController)
            }
            composable(Routes.EXPENSES_SCREEN) {
                ExpensesScreen(context, navController)
            }
            composable(Routes.SETTINGS_SCREEN) {
                SettingsScreen(context, navController)
            }
        }
    )
}