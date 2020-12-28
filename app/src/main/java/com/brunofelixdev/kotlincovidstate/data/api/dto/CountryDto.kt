package com.brunofelixdev.kotlincovidstate.data.api.dto

import java.text.NumberFormat

data class CountryDto(
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

