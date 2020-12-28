package com.brunofelixdev.kotlincovidstate.data.api.dto

data class CountryStatisticsData(
    val response: List<Response>?
)

data class Response(
    val country: String?,
    val cases: Cases?,
    val deaths: Deaths?,
    val tests: Tests?,
    val day: String?,
    val time: String?,
)

data class Cases(
    val new: String?,
    val active: Long?,
    val critical: Long?,
    val recovered: Long?,
    val total: Long?
)

data class Deaths(
    val new: String?,
    val total: Long?
)

data class Tests(
    val total: Long?
)