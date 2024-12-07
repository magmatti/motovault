package com.uken.motovault.report_generation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.util.Log
import com.uken.motovault.vindecoderAPI.Vehicle
import java.io.File
import java.io.FileOutputStream

class VehicleReportGenerator {

    private val pdfDocument = PdfDocument()
    private val page = createPdfDocument(pdfDocument)
    private val paint = Paint()
    private val canvas = page.canvas

    companion object {
        const val TAG = "VehicleReportGenerator"
    }

    fun generatePdfReport(context: Context, vehicle: Vehicle): File? {
        drawHeader(canvas, vehicle)
        drawVehicleDetails(canvas, vehicle)
        pdfDocument.finishPage(page)

        return saveToFile(context, pdfDocument)
    }

    private fun createPdfDocument(pdfDocument: PdfDocument): PdfDocument.Page {
        // return pdf document with A4 sizing in pixels
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        return pdfDocument.startPage(pageInfo)
    }

    private fun drawHeader(canvas: Canvas, vehicle: Vehicle) {
        paint.textSize = 18f
        paint.isFakeBoldText = true
        canvas.drawText("Vehicle Information Report", 200f, 50f, paint)
        paint.textSize = 16f
        paint.isFakeBoldText = false
        canvas.drawText("VIN number: ${vehicle.vin}", 50f, 80f, paint)
        canvas.drawText("Make: ${vehicle.make} | Model: ${vehicle.model}", 50f, 100f, paint)
        canvas.drawText("Model Year: ${vehicle.modelYear}", 50f, 120f, paint)
        canvas.drawText("Manufacturer: ${vehicle.manufacturer}", 50f, 140f, paint)
        canvas.drawText("Plant Country: ${vehicle.plantCountry}", 50f, 160f, paint)
    }

    private fun drawVehicleDetails(canvas: Canvas, vehicle: Vehicle) {
        paint.textSize = 12f
        var yPosition = 180f

        yPosition = drawSection(
            canvas,
            yPosition,
            "Engine Specifications",
            listOf(
                "Displacement: ${vehicle.engineDisplacement}",
                "Power (kW): ${vehicle.enginePowerKw}",
                "Power (HP): ${vehicle.enginePowerHp}",
                "Fuel Type: ${vehicle.fuelType}",
                "Engine Code: ${vehicle.engineCode}",
                "Cylinders: ${vehicle.engineCylinders}",
                "Cylinder Position: ${vehicle.engineCylindersPosition}",
                "Cylinder Bore: ${vehicle.engineCylinderBore}",
                "Stroke: ${vehicle.engineStroke}",
                "Oil Capacity: ${vehicle.engineOilCapacity}",
                "Position: ${vehicle.enginePosition}",
                "RPM: ${vehicle.engineRpm}",
                "Turbine: ${vehicle.engineTurbine}",
                "Valve Train: ${vehicle.valveTrain}",
                "Valves per Cylinder: ${vehicle.valvesPerCylinder}"
            )
        )

        yPosition = drawSection(
            canvas,
            yPosition,
            "Performance and Efficiency",
            listOf(
                "Max Speed: ${vehicle.maxSpeed} km/h",
                "Fuel Consumption (Combined): ${vehicle.fuelConsumptionCombined} l/100km",
                "Fuel Consumption (Extra Urban): ${vehicle.fuelConsumptionExtraUrban} l/100km",
                "Fuel Consumption (Urban): ${vehicle.fuelConsumptionUrban} l/100km",
                "Average CO2 Emission: ${vehicle.averageCO2Emission} g/km"
            )
        )

        yPosition = drawSection(
            canvas,
            yPosition,
            "Transmission Specifications",
            listOf(
                "Transmission: ${vehicle.transmission}",
                "Number of Gears: ${vehicle.numberOfGears}"
            )
        )

        yPosition = drawSection(
            canvas,
            yPosition,
            "Dimensions and Weight",
            listOf(
                "Height: ${vehicle.height} mm",
                "Length: ${vehicle.length} mm",
                "Width: ${vehicle.width} mm",
                "Wheelbase: ${vehicle.wheelbase} mm",
                "Track Rear: ${vehicle.trackRear} mm",
                "Empty Weight: ${vehicle.weightEmpty} kg"
            )
        )
    }

    private fun drawSection(
        canvas: Canvas,
        startY: Float,
        title: String,
        details: List<String>
    ): Float {
        var yPosition = startY
        paint.isFakeBoldText = true
        canvas.drawText(title, 50f, yPosition, paint)
        yPosition += 20f
        paint.isFakeBoldText = false
        details.forEach {
            canvas.drawText(it, 60f, yPosition, paint)
            yPosition += 20f
        }

        // creating extra space after section
        yPosition += 10f
        return yPosition
    }

    private fun saveToFile(context: Context, pdfDocument: PdfDocument): File? {
        val filePath = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            "MotoVaultCarInfoReport.pdf"
        )

        return try {
            pdfDocument.writeTo(FileOutputStream(filePath))
            Log.d(TAG, "saveToFile: pdf saved to file $filePath")
            filePath
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "saveToFile: error when saving file: $e")
            null
        } finally {
            pdfDocument.close()
        }
    }
}