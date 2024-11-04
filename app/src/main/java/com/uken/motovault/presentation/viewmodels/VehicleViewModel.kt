package com.uken.motovault.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uken.motovault.vindecoderAPI.APIConnector
import com.uken.motovault.vindecoderAPI.Vehicle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VehicleViewModel : ViewModel() {

    private val apiConnector = APIConnector()
    var vehicleState by mutableStateOf<Vehicle?>(null)
        private set

    fun fetchVehicleInfo(vin: String) {
        viewModelScope.launch {
            vehicleState = withContext(Dispatchers.IO) {
                apiConnector.getInfo(vin)
            }
        }
    }
}