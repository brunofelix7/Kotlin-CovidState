package com.brunofelixdev.kotlincovidstate.listener

import android.view.View
import androidx.lifecycle.LiveData
import com.brunofelixdev.kotlincovidstate.model.CountryData
import com.brunofelixdev.kotlincovidstate.model.WorldData

interface DataListener {

    fun onStarted()
    fun onCompletedWorldData(liveData: LiveData<List<WorldData>>)
    fun onCompletedCountriesData(liveData: LiveData<List<CountryData>>)
    fun onItemClick(view: View, country: String?)

}