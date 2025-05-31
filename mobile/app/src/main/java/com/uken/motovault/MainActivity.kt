package com.uken.motovault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.android.gms.auth.api.identity.Identity
import com.uken.motovault.sign_in.google_sign_in.GoogleAuthUiClient
import com.uken.motovault.ui.AppNavigation
import com.uken.motovault.ui.theme.MotoVaultTheme

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MotoVaultTheme {
                AppNavigation(this, googleAuthUiClient)
            }
        }
    }
}