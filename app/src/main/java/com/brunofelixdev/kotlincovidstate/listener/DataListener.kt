package com.brunofelixdev.kotlincovidstate.listener

import android.view.View
import androidx.lifecycle.LiveData
import com.brunofelixdev.kotlincovidstate.model.CountryData
import com.brunofelixdev.kotlincovidstate.model.WorldData

interface DataListener {

    fun onStarted()
    fun onSuccessWorldData(liveData: LiveData<List<WorldData>>)
    fun onSuccessCountriesData(liveData: LiveData<List<CountryData>>)
    fun onError(message: String)
    fun onItemClick(view: View, country: String?)

}