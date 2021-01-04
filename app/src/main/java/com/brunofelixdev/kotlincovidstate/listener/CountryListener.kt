package com.brunofelixdev.kotlincovidstate.listener

import android.view.View
import com.brunofelixdev.kotlincovidstate.data.api.response.CountryResponse

interface CountryListener {

    fun onCountriesStarted()
    fun onCountriesSuccess(data: List<CountryResponse>?)
    fun onCountriesError(message: String)
    fun onCountryItemClick(view: View, country: String?)

}