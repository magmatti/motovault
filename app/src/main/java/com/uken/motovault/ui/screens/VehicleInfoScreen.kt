package com.uken.motovault.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uken.motovault.presentation.viewmodels.VehicleViewModel
import com.uken.motovault.ui.composables.vehicle_info_screen.VehicleDetails

@Composable
fun VehicleInfoScreen(vehicleViewModel: VehicleViewModel = viewModel(), vin: String) {

    val vehicle = vehicleViewModel.vehicleState

    LaunchedEffect(vin) {
        vehicleViewModel.fetchVehicleInfo(vin)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Car info",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .align(Alignment.Start)
        )
        if (vehicle != null) {
            VehicleDetails(vehicle.vin.toString(), "VIN")
            VehicleDetails(vehicle.make.toString(), "Make")
            VehicleDetails(vehicle.model.toString(), "Model")
            VehicleDetails(vehicle.modelYear.toString(), "Model Year")
            VehicleDetails(vehicle.fuelType.toString(), "Fuel type")
            VehicleDetails(vehicle.numberOfDoors.toString(), "Number of doors")
            VehicleDetails(vehicle.numberOfGears.toString(), "Number of gears")
            VehicleDetails(vehicle.engineCode.toString(), "Engine code")
            VehicleDetails(vehicle.enginePowerHp.toString(), "Engine power")
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
    }

}
