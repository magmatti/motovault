package com.uken.motovault.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.uken.motovault.ui.Routes

class NavigationBarViewModel: ViewModel() {

    var selectedItemIndex by mutableIntStateOf(0)
        private set

    private val routes = listOf(
        Routes.HOME_SCREEN,
        Routes.EXPENSES_SCREEN,
        Routes.SERVICE_SCREEN,
        Routes.INSIGHTS_SCREEN
    )

    fun onItemSelected(index: Int) {
        selectedItemIndex = index
    }

    fun updateSelectedIndexByRoute(route: String?) {
        selectedItemIndex = routes.indexOf(route).takeIf { it >= 0 } ?: 0
    }
}