package com.brunofelixdev.kotlincovidstate.listener

import com.brunofelixdev.kotlincovidstate.data.api.response.CountryLocationResponse

interface CountryLocationListener {

    fun onStarted()
    fun onSuccess(data: List<CountryLocationResponse>?)
    fun onError(message: String)

}