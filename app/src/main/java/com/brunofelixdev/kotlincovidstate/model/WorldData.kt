package com.brunofelixdev.kotlincovidstate.model

import java.text.NumberFormat

data class WorldData(
        val confirmed: Long?,
        val recovered: Long?,
        val critical: Long?,
        val deaths: Long?,
        val lastChange: String?,
        val lastUpdate: String?
) {
    fun formattedNumber(number: Long?) : String {
        return NumberFormat.getInstance().format(number)
    }
}