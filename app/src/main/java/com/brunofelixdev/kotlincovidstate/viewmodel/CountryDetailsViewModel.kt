package com.brunofelixdev.kotlincovidstate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brunofelixdev.kotlincovidstate.data.api.repository.CountryDetailsRepository
import com.brunofelixdev.kotlincovidstate.handler.NoInternetException
import com.brunofelixdev.kotlincovidstate.listener.CountryDetailsListener
import com.brunofelixdev.kotlincovidstate.util.Coroutines
import com.google.android.gms.common.api.ApiException

class CountryDetailsViewModel(private val repository: CountryDetailsRepository) : ViewModel() {

    var confirmed: String? = null
    var activeCases: String? = null
    var newCases: String? = null
    var recovered: String? = null
    var critical: String? = null
    var deaths: String? = null
    var newDeaths: String? = null
    var testsDone: String? = null
    var fatalityRate: String? = null
    var recoveredRate: String? = null

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

    class DetailsViewModelFactory(private val repository: CountryDetailsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CountryDetailsViewModel(repository) as T
        }
    }

}