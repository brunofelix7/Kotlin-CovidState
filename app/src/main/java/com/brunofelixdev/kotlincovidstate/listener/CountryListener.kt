package com.brunofelixdev.kotlincovidstate.listener

import android.view.View
import com.brunofelixdev.kotlincovidstate.data.api.dto.CountryDto
import com.brunofelixdev.kotlincovidstate.data.api.dto.WorldTotalDto

interface CountryListener {

    fun onCountriesStarted()
    fun onCountriesSuccess(data: List<CountryDto>?)
    fun onCountriesError(message: String)
    fun onCountryItemClick(view: View, country: String?)

}