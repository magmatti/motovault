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
        startDestination = Routes.LoginScreen,
        builder = {
            composable(Routes.LoginScreen) {
                LoginScreen(context)
            }
            composable(Routes.HomeScreen) {
                HomeScreen(context)
            }
            composable(Routes.ExpensesScreen) {
                ExpensesScreen(context)
            }
            composable(Routes.SettingsScreen) {
                SettingsScreen(context)
            }
        }
    )
}