package com.brunofelixdev.kotlincovidstate.data.api.response

data class CountryLocationResponse(
    val country: String?,
    val confirmed: Long?,
    val deaths: Long?,
    val latitude: Double?,
    val longitude: Double?,
)