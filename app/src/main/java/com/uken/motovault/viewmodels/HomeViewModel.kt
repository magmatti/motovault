package com.uken.motovault.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uken.motovault.models.VehicleModel
import com.uken.motovault.retrofit.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val _vehicles = MutableStateFlow<List<VehicleModel>>(emptyList())
    val vehicles: StateFlow<List<VehicleModel>> = _vehicles

    init {
        fetchVehicles()
    }

    private fun fetchVehicles() {
        viewModelScope.launch {
            try {
                val fetchedVehicles = RetrofitInstance.vehiclesApi.getVehicles()
                _vehicles.value = fetchedVehicles
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}