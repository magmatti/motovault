package com.uken.motovault.ui.screens.about_app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.uken.motovault.ui.composables.misc.TopAppBarWithBackButton
import com.uken.motovault.ui.composables.navigationbar.AppNavigationBar

@Composable
fun AboutAppScreen(
    navController: NavController,
) {
    Scaffold(
        topBar = { TopAppBarWithBackButton("About App", navController) },
        bottomBar = { AppNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AboutAppContent()
        }
    }
}