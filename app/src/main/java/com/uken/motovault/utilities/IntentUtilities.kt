package com.uken.motovault.utilities

import android.content.Context
import android.content.Intent
import com.uken.motovault.app_settings.Constants

object IntentUtilities {

    fun startSupportEmailIntent(context: Context) {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "plain/text"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(Constants.SUPPORT_EMAIL))
            putExtra(Intent.EXTRA_SUBJECT, "Bug Report")
            putExtra(Intent.EXTRA_TEXT, "Bug description: ")
        }
        context.startActivity(emailIntent)
    }
}