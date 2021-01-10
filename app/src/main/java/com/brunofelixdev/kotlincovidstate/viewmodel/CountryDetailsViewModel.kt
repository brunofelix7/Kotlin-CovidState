package com.brunofelixdev.kotlincovidstate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brunofelixdev.kotlincovidstate.data.api.repository.CountryDetailsRepository
import com.brunofelixdev.kotlincovidstate.handler.ApiException
import com.brunofelixdev.kotlincovidstate.handler.NoInternetException
import com.brunofelixdev.kotlincovidstate.listener.CountryDetailsListener
import com.brunofelixdev.kotlincovidstate.util.Coroutines

class CountryDetailsViewModel(private val repository: CountryDetailsRepository) : ViewModel() {

    var listener: CountryDetailsListener? = null

    fun getStatistics(country: String) {
        listener?.onStarted()

        Coroutines.main {
            try {
                val response = repository.fetchCountryStatistics(country)

                listener?.onCompletedStatisticsData(response)
            } catch (e: ApiException) {
                listener?.onError(e.message ?: "")
            } catch (e: NoInternetException) {
                listener?.onError(e.message ?: "")
            }
        }
    }

    class CountryDetailsViewModelFactory(private val repository: CountryDetailsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CountryDetailsViewModel(repository) as T
        }
    }

}