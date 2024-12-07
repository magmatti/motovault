package com.uken.motovault.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uken.motovault.report_generation.VehicleReportGenerator
import com.uken.motovault.vindecoderAPI.APIConnector
import com.uken.motovault.vindecoderAPI.Vehicle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

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

    fun generateCarInfoPdf(context: Context): File? {
        val pdfGenerator = VehicleReportGenerator()

        return pdfGenerator.generatePdfReport(context, vehicleState!!)
    }
}