package com.uken.motovault.models

data class ServiceModel(
    val id: Int? = null,
    val vehicleId: Int,
    val serviceType: String,
    val date: String,
    val total: Double,
    val mail: String
)
