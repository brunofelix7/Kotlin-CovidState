package com.brunofelixdev.kotlincovidstate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brunofelixdev.kotlincovidstate.data.api.repository.DataRepository
import com.brunofelixdev.kotlincovidstate.handler.NoInternetException
import com.brunofelixdev.kotlincovidstate.listener.DataListener
import com.google.android.gms.common.api.ApiException

class DataViewModel(private val repository: DataRepository) : ViewModel() {

    var listener: DataListener? = null

    fun listAllData() {
        listener?.onStarted()
        try {
            listener?.onSuccessWorldData(repository.fetchWorldData())
            listener?.onSuccessCountriesData(repository.fetchAllCountries())
        } catch (e: ApiException) {
            listener?.onError(e.message ?: "")
        } catch (e: NoInternetException) {
            listener?.onError(e.message ?: "")
        }
    }

    class DataViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DataViewModel(repository) as T
        }
    }

}