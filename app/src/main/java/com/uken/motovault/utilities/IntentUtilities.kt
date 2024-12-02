package com.uken.motovault.utilities

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.util.Log
import com.uken.motovault.app_settings.Constants
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

object IntentUtilities {

    const val TAG = "IntentUtilities"

    fun startSupportEmailIntent(context: Context) {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "plain/text"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(Constants.SUPPORT_EMAIL))
            putExtra(Intent.EXTRA_SUBJECT, "Bug Report")
            putExtra(Intent.EXTRA_TEXT, "Bug description: ")
        }
        context.startActivity(emailIntent)
    }

    fun addEventToCalendar(
        context: Context,
        title: String,
        description: String,
        startDate: String
    ) {
        var calendar = Calendar.getInstance()
        val parsedDate = parseStringToDateFormat(startDate)

        if (parsedDate != null) {
            calendar.apply {
                set(parsedDate.year, parsedDate.monthValue - 1, parsedDate.dayOfMonth, 0, 0)
                Log.d(TAG, "addEventToCalendar: $calendar")
            }
        }

        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, title)
            putExtra(CalendarContract.Events.DESCRIPTION, description)

            // setting event to last whole day
            putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendar.timeInMillis)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calendar.timeInMillis)
        }
        context.startActivity(intent)
    }

    private fun parseStringToDateFormat(startDate: String): LocalDate? {
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            LocalDate.parse(startDate, formatter)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}