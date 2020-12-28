package com.brunofelixdev.kotlincovidstate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brunofelixdev.kotlincovidstate.data.api.repository.StatisticsDataRepository
import com.brunofelixdev.kotlincovidstate.handler.NoInternetException
import com.brunofelixdev.kotlincovidstate.listener.StatisticsListener
import com.google.android.gms.common.api.ApiException

class DetailsViewModel(private val repository: StatisticsDataRepository) : ViewModel() {

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

    var listener: StatisticsListener? = null

    fun getStatistics(country: String) {
        listener?.onStarted()
        try {
            listener?.onCompletedStatisticsData(repository.fetchCountryStatistics(country))
        } catch (e: ApiException) {
            listener?.onError(e.message ?: "")
        } catch (e: NoInternetException) {
            listener?.onError(e.message ?: "")
        }
    }

    class DetailsViewModelFactory(private val repository: StatisticsDataRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DetailsViewModel(repository) as T
        }
    }

}