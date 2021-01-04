package com.brunofelixdev.kotlincovidstate.data.api.response

import java.text.NumberFormat

data class CountryResponse(
    val code: String?,
    val confirmed: Long?,
    val country: String?,
    val critical: Long?,
    val deaths: Long?,
    val recovered: Long?,
    val latitude: Double?,
    val longitude: Double?,
    val lastChange: String?,
    val lastUpdate: String?,
) {
    fun formattedNumber(number: Long?) : String {
        return NumberFormat.getInstance().format(number)
    }
}

