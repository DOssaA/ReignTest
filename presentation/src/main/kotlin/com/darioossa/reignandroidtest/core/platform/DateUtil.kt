package com.darioossa.reignandroidtest.core.platform

import android.content.Context
import com.darioossa.reignandroidtest.R
import com.google.gson.internal.bind.util.ISO8601Utils
import java.text.ParsePosition
import java.util.*

/**
 * Helper object for dates
 */
object DateUtil {

    /**
     * Formats the date received by minute or hour within a date today or as yesterday or else with android formatting standard
     */
    fun format(context: Context, date: String): String {
        val parsedDate = ISO8601Utils.parse(date, ParsePosition(0))
        val parsedCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        parsedCalendar.time = parsedDate
        val today = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

        if (parsedCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
            if(parsedCalendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
                return if (parsedCalendar.get(Calendar.HOUR_OF_DAY) == today.get(Calendar.HOUR_OF_DAY)) {
                    context.getString(R.string.hours_ago, today.get(Calendar.MINUTE) - parsedCalendar.get(Calendar.MINUTE))
                } else {
                    val timeDifference = today.get(Calendar.HOUR_OF_DAY) - parsedCalendar.get(Calendar.HOUR_OF_DAY)
                    if (timeDifference > 24) {
                        context.getString(R.string.yesterday)
                    }
                    context.getString(R.string.hours_ago, timeDifference)
                }
            } else if (parsedCalendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR - 1)) {
                return context.getString(R.string.yesterday)
            }
        }
        
        val dateFormat = android.text.format.DateFormat.getDateFormat(context)
        return dateFormat.format(parsedDate)
    }
}