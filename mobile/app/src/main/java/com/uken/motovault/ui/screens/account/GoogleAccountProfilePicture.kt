package com.uken.motovault.ui.screens.account

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import com.uken.motovault.sign_in.google_sign_in.UserData

@Composable
fun GoogleAccountProfilePicture(
    userData: UserData?,
    size: Dp
) {
    if (userData?.profilePictureUrl != null) {
        AsyncImage(
            model = userData.profilePictureUrl,
            contentDescription = "User profile picture",
            modifier = Modifier
                .size(size)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}