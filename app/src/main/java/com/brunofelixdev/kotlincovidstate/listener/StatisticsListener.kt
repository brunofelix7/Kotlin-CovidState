package com.brunofelixdev.kotlincovidstate.listener

import androidx.lifecycle.LiveData
import com.brunofelixdev.kotlincovidstate.model.CountryStatisticsData

interface StatisticsListener {

    fun onStarted()
    fun onError(message: String)
    fun onCompletedStatisticsData(liveData: LiveData<CountryStatisticsData>)

}