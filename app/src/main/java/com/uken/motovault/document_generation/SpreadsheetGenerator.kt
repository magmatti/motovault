package com.uken.motovault.document_generation

import android.content.Context
import android.os.Environment
import android.util.Log
import com.uken.motovault.models.ExpenseModel
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

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
        val file = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            "MotoVaultExpenseReport.xlsx"
        )

        return try {
            FileOutputStream(file).use { output ->
                workbook.write(output)
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "saveToFile: $e")
            null
        } finally {
            workbook.close()
        }
    }
}