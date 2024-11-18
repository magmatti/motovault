package com.uken.motovault.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uken.motovault.models.ServiceModel
import com.uken.motovault.retrofit.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ServicesViewModel: ViewModel() {

    private val _services = MutableStateFlow<List<ServiceModel>>(emptyList())
    val services: StateFlow<List<ServiceModel>> = _services

    init {
        fetchServices()
    }

    private fun fetchServices() {
        viewModelScope.launch {
            try {
                val fetchedServices = RetrofitInstance.servicesApi.getServices()
                _services.value = fetchedServices
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}