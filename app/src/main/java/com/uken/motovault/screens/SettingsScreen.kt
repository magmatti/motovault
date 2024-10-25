package com.uken.motovault.screens

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(context: Context) {
    Text(
        text = "Settings Screen",
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp),
        textAlign = TextAlign.Start
    )
}