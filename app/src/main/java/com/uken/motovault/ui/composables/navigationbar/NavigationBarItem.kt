package com.uken.motovault.ui.composables.navigationbar

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationBarItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)
