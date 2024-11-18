package com.uken.motovault.models

data class ServiceModel(
    val id: Int,
    val vehicleId: Int,
    val serviceType: String,
    val date: String,
    val total: Double
)
