package dev.haqim.headsup.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun stringToDate(dateInput: String, format: String = DATE_TIME_WITH_TIMEZONE_FORMAT): Date? {
    return try {
        val inputFormat = SimpleDateFormat(format, Locale.getDefault())
        inputFormat.parse(dateInput)
    }catch (e: Exception){
        try {
            val inputFormat = SimpleDateFormat(DATE_TIME_WITH_TIMEZONE_FORMAT, Locale.getDefault())
            inputFormat.parse(dateInput)
        }catch (e: Exception){
            null
        }
    }
}

fun stringToStringDate(
    dateInput: String, 
    sourceFormat: String = DATE_TIME_WITH_TIMEZONE_FORMAT,
    targetFormat: String = "dd MMMM yyyy HH:mm:ss",
    locale: Locale = Locale.getDefault()
): String {
    val originalFormat = stringToDate(dateInput, sourceFormat)
    val parsedDateTime = SimpleDateFormat(targetFormat, locale)
    return originalFormat?.let { parsedDateTime.format(it) } ?: "-"
}

fun dateToTimestamp(dateInput: Date): Long{
    return dateInput.time / 1000
}
const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
const val DATE_TIME_WITH_TIMEZONE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
fun stringToTimestamp(dateInput: String, format: String = DATE_TIME_FORMAT): Long?{
    val date = stringToDate(dateInput, format)
    return date?.let { dateToTimestamp(it) }
}

fun currentTimestamp() = System.currentTimeMillis() / 1000
