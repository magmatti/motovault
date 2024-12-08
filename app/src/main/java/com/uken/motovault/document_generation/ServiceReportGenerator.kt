package com.uken.motovault.document_generation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.util.Log
import com.uken.motovault.models.ServiceModel
import java.io.File
import java.io.FileOutputStream

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
        val filePath = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            "MotoVaultServicesReport.pdf"
        )

        return try {
            pdfDocument.writeTo(FileOutputStream(filePath))
            Log.d(TAG, "savePdfToFile: pdf saved to file $filePath")
            filePath
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "savePdfToFile: error when saving file: $e")
            null
        } finally {
            pdfDocument.close()
        }
    }
}