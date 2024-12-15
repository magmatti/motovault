package com.uken.motovault.document_generation

import android.content.ContentValues
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.uken.motovault.models.ServiceModel
import java.io.File

class ServiceReportGenerator {

    private val pdfDocument = PdfDocument()
    private val page = createPdfDocument(pdfDocument)
    private val canvas = page.canvas

    companion object {
        private const val TAG = "ServiceReportGenerator"
    }

    fun generatePdf(context: Context, services: List<ServiceModel>): File? {
        drawTitle(canvas)
        drawTableHeader(canvas)
        drawTableContent(canvas, services)

        pdfDocument.finishPage(page)

        return savePdfToFile(context, pdfDocument)
    }

    private fun createPdfDocument(pdfDocument: PdfDocument): PdfDocument.Page {
        // return pdf document with A4 sizing in pixels
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        return pdfDocument.startPage(pageInfo)
    }

    private fun drawTitle(canvas: Canvas) {
        val titlePaint = Paint().apply {
            textSize = 18f
            isFakeBoldText = true
        }
        canvas.drawText("Vehicle Services Report", 200f, 50f, titlePaint)
    }

    private fun drawTableHeader(canvas: Canvas) {
        val paint = Paint().apply { textSize = 12f }
        val yPosition = 100f

        canvas.drawText("Date", 50f, yPosition, paint)
        canvas.drawText("ServiceType", 150f, yPosition, paint)
        canvas.drawText("Total PLN", 350f, yPosition, paint)
    }

    private fun drawTableContent(canvas: Canvas, services: List<ServiceModel>) {
        val paint = Paint().apply { textSize = 12f }
        var yPosition = 120f
        val lineSpacing = 20f

        for (service in services) {
            canvas.drawText(service.date, 50f, yPosition, paint)
            canvas.drawText(service.serviceType, 150f, yPosition, paint)
            canvas.drawText(service.total.toString(), 350f, yPosition, paint)
            yPosition += lineSpacing
        }
    }

    private fun savePdfToFile(context: Context, pdfDocument: PdfDocument): File? {
        val fileName = "MotoVaultServicesReport.pdf"

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