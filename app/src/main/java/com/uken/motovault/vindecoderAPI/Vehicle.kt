package com.uken.motovault.vindecoderAPI

import org.json.JSONObject

class Vehicle(response: String) {
    var vin: String? = null
    var vehicleId: Int = 0
    var make: String? = null
    var model: String? = null
    var modelYear: Int = 0
    var productType: String? = null
    var body: String? = null
    var drive: String? = null
    var engineDisplacement: Int = 0
    var enginePowerKw: Int = 0
    var enginePowerHp: Int = 0
    var fuelType: String? = null
    var engineCode: String? = null
    var transmission: String? = null
    var numberOfGears: Int = 0
    var manufacturer: String? = null
    var manufacturerAddress: String? = null
    var plantCountry: String? = null
    var engineCylinderBore: Double = 0.0
    var engineCylinders: Int = 0
    var engineCylindersPosition: String? = null
    var engineOilCapacity: Int = 0
    var enginePosition: String? = null
    var engineRpm: Int = 0
    var engineStroke: Int = 0
    var engineTurbine: String? = null
    var valveTrain: String? = null
    var fuelConsumptionCombined: Double = 0.0
    var fuelConsumptionExtraUrban: Double = 0.0
    var fuelConsumptionUrban: Double = 0.0
    var averageCO2Emission: Double = 0.0
    var valvesPerCylinder: Int = 0
    var numberOfAxles: Int = 0
    var numberOfDoors: Int = 0
    var numberOfSeats: String? = null
    var powerSteering: String? = null
    var frontBrakes: String? = null
    var rearBrakes: String? = null
    var steeringType: String? = null
    var frontSuspension: String? = null
    var wheelSize: String? = null
    var wheelbase: Int = 0
    var height: Int = 0
    var length: Int = 0
    var width: Int = 0
    var trackRear: Int = 0
    var maxSpeed: Int = 0
    var weightEmpty: Int = 0
    var maxWeight: Int = 0
    var maxRoofLoad: Int = 0
    var permittedTrailerLoadWithoutBrakes: Int = 0
    var isAbs: Boolean = false
    var checkDigit: String? = null
    var sequentialNumber: String? = null

    init {
        val jsonResponse = JSONObject(response)
        val decodeArray = jsonResponse.getJSONArray("decode")

        for (i in 0 until decodeArray.length()) {
            val item = decodeArray.getJSONObject(i)
            val label = item.getString("label")
            val value = item["value"]

            when (label) {
                "VIN" -> this.vin = value as String
                "Vehicle ID" -> this.vehicleId = value as Int
                "Make" -> this.make = value as String
                "Model" -> this.model = value as String
                "Model Year" -> this.modelYear = value as Int
                "Product Type" -> this.productType = value as String
                "Body" -> this.body = value as String
                "Drive" -> this.drive = value as String
                "Engine Displacement (ccm)" -> this.engineDisplacement = value as Int
                "Engine Power (kW)" -> this.enginePowerKw = value as Int
                "Engine Power (HP)" -> this.enginePowerHp = value as Int
                "Fuel Type - Primary" -> this.fuelType = value as String
                "Engine Code" -> this.engineCode = value as String
                "Transmission" -> this.transmission = value as String
                "Number of Gears" -> this.numberOfGears = value as Int
                "Manufacturer" -> this.manufacturer = value as String
                "Manufacturer Address" -> this.manufacturerAddress = value as String
                "Plant Country" -> this.plantCountry = value as String
                "Engine Cylinder Bore (mm)" -> this.engineCylinderBore = (value as Number).toDouble()
                "Engine Cylinders" -> this.engineCylinders = value as Int
                "Engine Cylinders Position" -> this.engineCylindersPosition = value as String
                "Engine Oil Capacity (l)" -> this.engineOilCapacity = value as Int
                "Engine Position" -> this.enginePosition = value as String
                "Engine RPM" -> this.engineRpm = value as Int
                "Engine Stroke (mm)" -> this.engineStroke = value as Int
                "Engine Turbine" -> this.engineTurbine = value as String
                "Valve Train" -> this.valveTrain = value as String
                "Fuel Consumption Combined (l/100km)" -> this.fuelConsumptionCombined = (value as Number).toDouble()
                "Fuel Consumption Extra Urban (l/100km)" -> this.fuelConsumptionExtraUrban =
                    (value as Number).toDouble()

                "Fuel Consumption Urban (l/100km)" -> this.fuelConsumptionUrban = (value as Number).toDouble()
                "Average CO2 Emission (g/km)" -> this.averageCO2Emission = (value as Number).toDouble()
                "Valves per Cylinder" -> this.valvesPerCylinder = value as Int
                "Number of Axles" -> this.numberOfAxles = value as Int
                "Number of Doors" -> this.numberOfDoors = value as Int
                "Number of Seats" -> this.numberOfSeats = value as String
                "Power Steering" -> this.powerSteering = value as String
                "Front Brakes" -> this.frontBrakes = value as String
                "Rear Brakes" -> this.rearBrakes = value as String
                "Steering Type" -> this.steeringType = value as String
                "Front Suspension" -> this.frontSuspension = value as String
                "Wheel Size" -> this.wheelSize = value as String
                "Wheelbase (mm)" -> this.wheelbase = value as Int
                "Height (mm)" -> this.height = value as Int
                "Length (mm)" -> this.length = value as Int
                "Width (mm)" -> this.width = value as Int
                "Track Rear (mm)" -> this.trackRear = value as Int
                "Max Speed (km/h)" -> this.maxSpeed = value as Int
                "Weight Empty (kg)" -> this.weightEmpty = value as Int
                "Max Weight (kg)" -> this.maxWeight = value as Int
                "Max roof load (kg)" -> this.maxRoofLoad = value as Int
                "Permitted trailer load without brakes (kg)" -> this.permittedTrailerLoadWithoutBrakes = value as Int
                "ABS" -> this.isAbs = value as Int == 1
                "Check Digit" -> this.checkDigit = value as String
                "Sequential Number" -> this.sequentialNumber = value as String
            }
        }
    }
}
