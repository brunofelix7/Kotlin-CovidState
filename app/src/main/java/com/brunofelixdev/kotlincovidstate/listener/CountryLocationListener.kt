package com.brunofelixdev.kotlincovidstate.listener

import com.brunofelixdev.kotlincovidstate.data.api.dto.CountryLocationDto

interface CountryLocationListener {

    fun onStarted()
    fun onSuccess(data: List<CountryLocationDto>?)
    fun onError(message: String)

}