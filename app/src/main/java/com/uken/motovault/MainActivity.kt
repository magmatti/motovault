package com.uken.motovault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.uken.motovault.ui.composables.navigation.AppNavigation
import com.uken.motovault.ui.theme.MotoVaultTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MotoVaultTheme {
                AppNavigation(this)
            }
        }
    }
}
