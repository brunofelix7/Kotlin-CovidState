package com.brunofelixdev.kotlincovidstate.data.api.repository

import android.content.Context
import com.brunofelixdev.kotlincovidstate.R
import com.brunofelixdev.kotlincovidstate.data.api.Api
import com.brunofelixdev.kotlincovidstate.data.api.SafeApiRequest
import com.brunofelixdev.kotlincovidstate.data.api.response.CountryResponse
import com.brunofelixdev.kotlincovidstate.data.api.response.CountryLocationResponse
import com.brunofelixdev.kotlincovidstate.data.api.interceptor.NetworkConnectionInterceptor

class CountryRepository(
    private val context: Context,
    private val networkConnectionInterceptor: NetworkConnectionInterceptor
) :
    SafeApiRequest() {

    suspend fun fetchCountries(): List<CountryResponse> {
        return apiRequest {
            Api(
                context.resources.getString(R.string.api_url),
                networkConnectionInterceptor
            ).fetchAllCountries(
                context.resources.getString(R.string.api_host),
                context.resources.getString(R.string.api_key)
            )
        }
    }

    suspend fun fetchCountryLocation(): List<CountryLocationResponse> {
        return apiRequest {
            Api(
                context.resources.getString(R.string.api_url),
                networkConnectionInterceptor
            ).fetchCountryLocation(
                context.resources.getString(R.string.api_host),
                context.resources.getString(R.string.api_key)
            )
        }
    }

}