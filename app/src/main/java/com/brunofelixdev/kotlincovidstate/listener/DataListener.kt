package com.brunofelixdev.kotlincovidstate.listener

import androidx.lifecycle.LiveData
import com.brunofelixdev.kotlincovidstate.model.CountryData
import com.brunofelixdev.kotlincovidstate.model.WorldData

interface DataListener {

    fun onCompletedWorldData(liveData: LiveData<List<WorldData>>)
    fun onCompletedCountriesData(liveData: LiveData<List<CountryData>>)

}