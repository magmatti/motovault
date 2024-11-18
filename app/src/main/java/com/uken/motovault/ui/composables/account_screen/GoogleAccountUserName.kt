package com.uken.motovault.ui.composables.account_screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.uken.motovault.sign_in.google_sign_in.UserData

@Composable
fun GoogleAccountUserName(
    userData: UserData?,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    if (userData?.username != null) {
        Text(
            text = userData.username,
            textAlign = TextAlign.Center,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}