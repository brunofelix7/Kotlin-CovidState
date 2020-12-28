package com.brunofelixdev.kotlincovidstate.data.api.dto

data class CountryLocationDto(
    val country: String?,
    val confirmed: Long?,
    val deaths: Long?,
    val latitude: Double?,
    val longitude: Double?,
)