package com.uken.motovault.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uken.motovault.api.retrofit.RetrofitInstance
import com.uken.motovault.document_generation.ServiceReportGenerator
import com.uken.motovault.models.ServiceModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ServicesViewModel: ViewModel() {

    private val _services = MutableStateFlow<List<ServiceModel>>(emptyList())
    val services: StateFlow<List<ServiceModel>> = _services

    private val _lastOilChangeDate = MutableStateFlow<String?>(null)
    val lastOilChangeDate: StateFlow<String?> = _lastOilChangeDate

    private val _lastInspectionDate = MutableStateFlow<String?>(null)
    val lastInspectionDate: StateFlow<String?> = _lastInspectionDate

    companion object {
        const val TAG = "ServicesViewModel"
    }

    private fun updateSortedServices(newService: List<ServiceModel>) {
        _services.value = newService.sortedByDescending { it.date }
    }

    private fun getLatestServiceDate(serviceType: String): String? {
        return _services.value
            .filter { it.serviceType == serviceType }
            .maxByOrNull { LocalDate.parse(it.date, DateTimeFormatter.ISO_DATE) }
            ?.date
    }

    fun getServices(mail: String) {
        viewModelScope.launch {
            try {
                val fetchedServices = RetrofitInstance.servicesApi.getServices(mail)
                updateSortedServices(fetchedServices)
                _lastOilChangeDate.value = getLatestServiceDate("Oil service")
                _lastInspectionDate.value = getLatestServiceDate("Inspection")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addService(service: ServiceModel) {
        viewModelScope.launch {
            try {
                val addedService = RetrofitInstance.servicesApi.addService(service)
                updateSortedServices(_services.value + addedService)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateService(service: ServiceModel) {
        viewModelScope.launch {
            try {
                val updatedService = RetrofitInstance.servicesApi.updateService(
                    service.id!!,
                    service
                )
                val updatedServiceList = _services.value.map {
                    if (it.id == updatedService.id) updatedService else it
                }
                updateSortedServices(updatedServiceList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteService(id: Int) {
        viewModelScope.launch {
            try {
                val response: Response<Unit> = RetrofitInstance.servicesApi.deleteService(id)
                if (response.isSuccessful) {
                    val updatedList = _services.value.filter { it.id != id }
                    _services.value = updatedList
                    if (updatedList.isEmpty()) {
                        _services.value = emptyList()
                    }
                } else {
                    Log.d(TAG, "deleteService: $response")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun generateServicesPdf(context: Context): File? {
        val servicesList = services.value
        val pdfGenerator = ServiceReportGenerator()

        return pdfGenerator.generatePdf(context, servicesList)
    }
}