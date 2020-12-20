package com.brunofelixdev.kotlincovidstate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brunofelixdev.kotlincovidstate.data.api.repository.DataRepository
import com.brunofelixdev.kotlincovidstate.listener.DataListener

class DataViewModel(private val repository: DataRepository) : ViewModel() {

    var listener: DataListener? = null

    fun listAllData() {
        listener?.onStarted()
        listener?.onCompletedWorldData(repository.fetchWorldData())
        listener?.onCompletedCountriesData(repository.fetchAllCountries())
    }

    class DataViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DataViewModel(repository) as T
        }
    }

}