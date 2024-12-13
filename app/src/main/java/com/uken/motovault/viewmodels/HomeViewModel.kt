package com.uken.motovault.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uken.motovault.api.retrofit.RetrofitInstance
import com.uken.motovault.models.VehicleModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel: ViewModel() {

    private val _vehicles = MutableStateFlow<List<VehicleModel>>(emptyList())
    val vehicles: StateFlow<List<VehicleModel>> = _vehicles

    companion object {
        const val TAG = "HomeViewModel"
    }

    fun fetchVehicles(mail: String) {
        viewModelScope.launch {
            try {
                val fetchedVehicles = RetrofitInstance.vehiclesApi.getVehicles(mail)
                _vehicles.value = fetchedVehicles
                Log.d(TAG, "fetchVehicles: ${_vehicles.value}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addVehicle(vehicle: VehicleModel) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "addVehicle: $vehicle")
                val addedVehicle = RetrofitInstance.vehiclesApi.addVehicle(vehicle)
                _vehicles.value = _vehicles.value + addedVehicle
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteVehicle(id: Int, mail: String) {
        viewModelScope.launch {
            try {
                val response: Response<Unit> = RetrofitInstance.vehiclesApi.deleteVehicle(id)
                if (response.isSuccessful) {
                    fetchVehicles(mail)
                } else {
                    Log.d(TAG, "deleteVehicles: $response")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}