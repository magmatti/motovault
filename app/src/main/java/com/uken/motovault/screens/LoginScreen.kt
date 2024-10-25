package com.uken.motovault.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uken.motovault.navigation.Routes

@Composable
fun LoginScreen(context: Context, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login Screen",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(Alignment.Start),
        )
        Button(
            onClick = {
                onLoginButtonClick(Routes.HOME_SCREEN, navController)
            },
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
        ) {
            Text(text = "Login")
        }
    }
}

fun onLoginButtonClick(route: String, navController: NavController) {
    navController.navigate(route)
}