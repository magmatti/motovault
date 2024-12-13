package com.uken.motovault.ui.screens.vehicle_info

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.uken.motovault.sign_in.email_sign_in.EmailSignInViewModel
import com.uken.motovault.ui.composables.misc.PageInfoBox
import com.uken.motovault.ui.composables.misc.TopAppBarWithBackButton
import com.uken.motovault.ui.composables.navigationbar.AppNavigationBar
import com.uken.motovault.viewmodels.HomeViewModel
import com.uken.motovault.viewmodels.VehicleViewModel

@Composable
fun VehicleInfoScreen(
    navController: NavController,
    vehicleViewModel: VehicleViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel(),
    emailSignInViewModel: EmailSignInViewModel = viewModel()
) {
    val context = LocalContext.current
    val vehicle = vehicleViewModel.vehicleState
    val userEmail by emailSignInViewModel.userEmail.observeAsState()

    val vehicleId = navController.currentBackStackEntry?.arguments?.getString("id")
    val vehicleVin = navController.currentBackStackEntry?.arguments?.getString("vin")

    LaunchedEffect(Unit) {
        vehicleViewModel.fetchVehicleInfo(vehicleVin!!)
    }

    Scaffold(
        topBar = {
            TopAppBarWithBackButton(
                "Car Info",
                navController,
                "Delete car",
                onActionClick = {
                    homeViewModel.deleteVehicle(vehicleId!!.toInt(), userEmail!!)
                    navController.popBackStack()
                }
            )
        },
        bottomBar = { AppNavigationBar(navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                PageInfoBox(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info"
                )
                TextButton(
                    onClick = {
                        if (vehicle != null) {
                            val file = vehicleViewModel.generateCarInfoPdf(context)
                            if (file != null) {
                                Toast.makeText(context,
                                    "PDF saved to ${file.absolutePath}",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Failed to generate PDF.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    },
                ) {
                    Text(
                        "Save to PDF",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Right
                    )
                }
            }

            if (vehicle != null) {
                item {
                    VehicleInfoCard(title = "Vehicle Information") {
                        Text("VIN: ${vehicle.vin}")
                        Text("Make: ${vehicle.make}")
                        Text("Model: ${vehicle.model}")
                        Text("Model Year: ${vehicle.modelYear}")
                        Text("Number of Seats: ${vehicle.numberOfSeats}")
                        Text("Manufacturer: ${vehicle.manufacturer}")
                        Text("Plant Country: ${vehicle.plantCountry}")
                        Text("Product Type: ${vehicle.productType}")
                    }
                }

                item {
                    VehicleInfoCard(title = "Engine Specifications") {
                        Text("Displacement: ${vehicle.engineDisplacement}")
                        Text("Power (kW): ${vehicle.enginePowerKw}")
                        Text("Power (HP): ${vehicle.enginePowerHp}")
                        Text("Fuel Type: ${vehicle.fuelType}")
                        Text("Engine Code: ${vehicle.engineCode}")
                        Text("Cylinders: ${vehicle.engineCylinders}")
                        Text("Cylinder Position: ${vehicle.engineCylindersPosition}")
                        Text("Cylinder Bore: ${vehicle.engineCylinderBore}")
                        Text("Stroke: ${vehicle.engineStroke}")
                        Text("Oil Capacity: ${vehicle.engineOilCapacity}")
                        Text("Position: ${vehicle.enginePosition}")
                        Text("RPM: ${vehicle.engineRpm}")
                        Text("Turbine: ${vehicle.engineTurbine}")
                        Text("Valve Train: ${vehicle.valveTrain}")
                        Text("Valves per Cylinder: ${vehicle.valvesPerCylinder}")
                    }
                }

                item {
                    VehicleInfoCard(title = "Performance and Efficiency") {
                        Text("Max Speed: ${vehicle.maxSpeed} km/h")
                        Text("Fuel Consumption (Combined): ${vehicle.fuelConsumptionCombined} l/100km")
                        Text("Fuel Consumption (Extra Urban): ${vehicle.fuelConsumptionExtraUrban} l/100km")
                        Text("Fuel Consumption (Urban): ${vehicle.fuelConsumptionUrban} l/100km")
                        Text("Average CO2 Emission: ${vehicle.averageCO2Emission} g/km")
                    }
                }

                item {
                    VehicleInfoCard(title = "Transmission Specifications") {
                        Text("Transmission: ${vehicle.transmission}")
                        Text("Number of Gears: ${vehicle.numberOfGears}")
                    }
                }

                item {
                    VehicleInfoCard(title = "Dimensions and Weight") {
                        Text("Height: ${vehicle.height} mm")
                        Text("Length: ${vehicle.length} mm")
                        Text("Width: ${vehicle.width} mm")
                        Text("Wheelbase: ${vehicle.wheelbase} mm")
                        Text("Track Rear: ${vehicle.trackRear} mm")
                        Text("Empty Weight: ${vehicle.weightEmpty} kg")
                        Text("Max Weight: ${vehicle.maxWeight} kg")
                        Text("Max Roof Load: ${vehicle.maxRoofLoad} kg")
                        Text("Trailer Load Without Brakes: ${vehicle.permittedTrailerLoadWithoutBrakes} kg")
                    }
                }

                item {
                    VehicleInfoCard(title = "Brakes and Steering") {
                        Text("Front Brakes: ${vehicle.frontBrakes}")
                        Text("Rear Brakes: ${vehicle.rearBrakes}")
                        Text("Steering Type: ${vehicle.steeringType}")
                        Text("Power Steering: ${vehicle.powerSteering}")
                        Text("ABS: ${vehicle.isAbs}")
                    }
                }

                item {
                    VehicleInfoCard(title = "Suspension and Wheels") {
                        Text("Front Suspension: ${vehicle.frontSuspension}")
                        Text("Wheel Size: ${vehicle.wheelSize}")
                    }
                }

                item {
                    VehicleInfoCard(title = "Axles and Doors") {
                        Text("Number of Axles: ${vehicle.numberOfAxles}")
                        Text("Number of Doors: ${vehicle.numberOfDoors}")
                    }
                }
            } else {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}