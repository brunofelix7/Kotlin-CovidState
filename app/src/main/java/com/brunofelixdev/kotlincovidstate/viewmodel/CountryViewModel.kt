package com.brunofelixdev.kotlincovidstate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brunofelixdev.kotlincovidstate.data.api.repository.CountryRepository
import com.brunofelixdev.kotlincovidstate.handler.NoInternetException
import com.brunofelixdev.kotlincovidstate.listener.CountryListener
import com.brunofelixdev.kotlincovidstate.listener.CountryLocationListener
import com.brunofelixdev.kotlincovidstate.util.Coroutines
import com.google.android.gms.common.api.ApiException

class CountryViewModel(private val repository: CountryRepository) : ViewModel() {

    var listener: CountryListener? = null
    var listenerCountryLocation: CountryLocationListener? = null

    fun listCountries() {
        listener?.onCountriesStarted()
        try {
            Coroutines.main {
                val response = repository.fetchCountries()

                if (response.isSuccessful) {
                    listener?.onCountriesSuccess(response.body())
                } else {
                    listener?.onCountriesError("Error code: ${response.code()}")
                }
            }
        } catch (e: ApiException) {
            listener?.onCountriesError(e.message ?: "")
        } catch (e: NoInternetException) {
            listener?.onCountriesError(e.message ?: "")
        }
    }

    fun listCountryLocation() {
        listenerCountryLocation?.onStarted()
        try {
            Coroutines.main {
                val response = repository.fetchCountryLocation()

                if (response.isSuccessful) {
                    listenerCountryLocation?.onSuccess(response.body())
                } else {
                    listenerCountryLocation?.onError("Error code: ${response.code()}")
                }
            }
        } catch (e: ApiException) {
            listenerCountryLocation?.onError(e.message ?: "")
        } catch (e: NoInternetException) {
            listenerCountryLocation?.onError(e.message ?: "")
        }
    }

    class CountryViewModelFactory(private val repository: CountryRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CountryViewModel(repository) as T
        }
    }

}