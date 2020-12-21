package com.brunofelixdev.kotlincovidstate.extension

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.dateFormatted() : String {
    val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
    val output = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return try {
        val timeZone = TimeZone.getTimeZone(this)
        df.timeZone = TimeZone.getTimeZone(timeZone.id)

        val result = df.parse(this) ?: "Desconhecido"

        output.format(result)
    } catch (e: ParseException) {
        "Desconhecido"
    }
}