package com.uken.motovault.document_generation

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.uken.motovault.models.ExpenseModel
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File

class SpreadsheetGenerator() {

    val workbook: Workbook = XSSFWorkbook()
    val sheet = workbook.createSheet("Expenses")

    companion object {
        const val TAG = "SpreadsheetGenerator"
    }

    fun generateExpenseReport(context: Context, expenses: List<ExpenseModel>): File? {
        createHeaderRow(sheet)
        populateDataRows(sheet, expenses)
        val file = saveToFile(context)

        return file
    }

    private fun createHeaderRow(sheet: org.apache.poi.ss.usermodel.Sheet) {
        val headerRow = sheet.createRow(0)
        val headers = listOf("ID", "ExpenseType", "Total", "Date")
        headers.forEachIndexed { index, header ->
            headerRow.createCell(index).setCellValue(header)
        }
    }

    private fun populateDataRows(
        sheet: org.apache.poi.ss.usermodel.Sheet,
        expenses: List<ExpenseModel>
    ) {
        expenses.forEachIndexed { rowIndex, expense ->
            val row = sheet.createRow(rowIndex + 1)
            row.createCell(0).setCellValue(expense.id!!.toDouble())
            row.createCell(1).setCellValue(expense.expensesType)
            row.createCell(2).setCellValue(expense.total)
            row.createCell(3).setCellValue(expense.date)
        }
    }

    private fun saveToFile(context: Context): File? {
        val fileName = "MotoVaultExpenseSheet.xls"

        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, fileName)
            put(MediaStore.Downloads.MIME_TYPE, "application/vnd.ms-excel")
            put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        return if (uri != null) {
            try {
                resolver.openOutputStream(uri).use { outputStream ->
                    if (outputStream != null) {
                        workbook.write(outputStream)
                        Log.d(TAG, "saveToFile: Sheet file saved to Documents folder")
                    } else {
                        Log.e(TAG, "saveToFile: OutputStream is null")
                    }
                }
                File(uri.path)
            } catch (e: Exception) {
                Log.e(TAG, "saveToFile: Error saving Sheet file $e")
                null
            } finally {
                workbook.close()
            }
        } else {
            Log.e(TAG, "saveToFile: Failed to create MediaStore entry")
            null
        }
    }
}