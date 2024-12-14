package com.uken.motovault.models

data class VehicleModel(
    val vin: String,
    val mail: String,
    val id: Int?,
    var make: String?,
    var model: String?
)
