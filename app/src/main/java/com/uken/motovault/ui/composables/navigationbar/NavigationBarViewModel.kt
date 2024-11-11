package com.uken.motovault.ui.composables.navigationbar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.uken.motovault.ui.Routes

class NavigationBarViewModel: ViewModel() {

    var selectedItemIndex by mutableStateOf(0)
        private set

    private val routes = listOf(
        Routes.HOME_SCREEN,
        Routes.EXPENSES_SCREEN,
        Routes.SETTINGS_SCREEN,
        Routes.ACCOUNT_SCREEN
    )

    fun onItemSelected(index: Int) {
        selectedItemIndex = index
    }

    fun updateSelectedIndexByRoute(route: String?) {
        selectedItemIndex = routes.indexOf(route).takeIf { it >= 0 } ?: 0
    }
}