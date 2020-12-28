package com.brunofelixdev.kotlincovidstate.listener

import com.brunofelixdev.kotlincovidstate.data.api.dto.CountryStatisticsData

interface CountryDetailsListener {

    fun onStarted()
    fun onError(message: String)
    fun onCompletedStatisticsData(data: CountryStatisticsData?)

}