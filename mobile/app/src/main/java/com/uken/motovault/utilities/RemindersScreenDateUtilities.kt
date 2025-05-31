package com.uken.motovault.utilities

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object RemindersScreenDateUtilities {

    fun calculateNextActionDate(date: String): String {
        val lastInspectionDateParsed = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
        val nextInspectionDate = lastInspectionDateParsed.plusYears(1)
        val nextInspectionDateString = nextInspectionDate.format(DateTimeFormatter.ISO_DATE)

        return nextInspectionDateString
    }

    fun getServiceStatus(serviceDate: String?): String {
        serviceDate?.let {
            val serviceDateParsed = LocalDate.parse(it, DateTimeFormatter.ISO_DATE)
            val currentDate = LocalDate.now()
            val yearsBetween = ChronoUnit.YEARS.between(serviceDateParsed, currentDate)
            return if (yearsBetween < 1) "Valid" else "Expired"
        }
        return "Unknown"
    }

    fun getOilChangeStatus(lastOilChangeDate: String?, oilChangeInterval: String): String {
        lastOilChangeDate?.let {
            val lastOilChangeDateParsed = LocalDate.parse(it, DateTimeFormatter.ISO_DATE)
            val currentDate = LocalDate.now()

            val nextOilChangeDate = when (oilChangeInterval) {
                "Every year" -> lastOilChangeDateParsed.plusYears(1)
                "6 months" -> lastOilChangeDateParsed.plusMonths(6)
                "3 months" -> lastOilChangeDateParsed.plusMonths(3)
                else -> return "Unknown"
            }

            return if (currentDate.isBefore(nextOilChangeDate)) "Valid" else "Expired"
        }
        return "Unknown"
    }
}