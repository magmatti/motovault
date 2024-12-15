package com.uken.motovault.document_generation

import android.content.ContentValues
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.uken.motovault.api.vindecoderAPI.Vehicle
import java.io.File

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
        val fileName = "MotoVaultVehicleReport.pdf"

        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, fileName)
            put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
            put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        return if (uri != null) {
            try {
                resolver.openOutputStream(uri).use { outputStream ->
                    if (outputStream != null) {
                        pdfDocument.writeTo(outputStream)
                        Log.d(TAG, "savePdfToFile: PDF saved to Downloads folder")
                    } else {
                        Log.e(TAG, "savePdfToFile: OutputStream is null")
                    }
                }
                File(uri.path)
            } catch (e: Exception) {
                Log.e(TAG, "savePdfToFile: Error saving PDF $e")
                null
            } finally {
                pdfDocument.close()
            }
        } else {
            Log.e(TAG, "savePdfToFile: Failed to create MediaStore entry")
            null
        }
    }
}