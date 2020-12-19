package com.brunofelixdev.kotlincovidstate.extension

import java.text.DecimalFormat
import java.text.NumberFormat

fun Long.formatNumber() : String {
    return NumberFormat.getInstance().format(this)
}

fun Long.fatalityRate(confirmed: Long?) : String {
    val result = (100.0 * this) / confirmed!!
    return DecimalFormat("#.##").format(result)
}

fun Long.recoveredRate(confirmed: Long?) : String {
    val result = (100.0 * this) / confirmed!!
    return DecimalFormat("#.##").format(result)
}