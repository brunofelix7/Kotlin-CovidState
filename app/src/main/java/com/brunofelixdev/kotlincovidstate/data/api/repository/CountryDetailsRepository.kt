package com.brunofelixdev.kotlincovidstate.data.api.repository

import android.content.Context
import com.brunofelixdev.kotlincovidstate.R
import com.brunofelixdev.kotlincovidstate.data.api.Api
import com.brunofelixdev.kotlincovidstate.data.api.SafeApiRequest
import com.brunofelixdev.kotlincovidstate.data.api.interceptor.NetworkConnectionInterceptor
import com.brunofelixdev.kotlincovidstate.data.api.response.CountryStatisticsData

class CountryDetailsRepository(
    private val context: Context,
    private val networkConnectionInterceptor: NetworkConnectionInterceptor
) :
    SafeApiRequest() {

    suspend fun fetchCountryStatistics(country: String): CountryStatisticsData {
        return apiRequest {
            Api(
                context.resources.getString(R.string.api_url_statistics),
                networkConnectionInterceptor
            ).fetchCountryStatistics(
                context.resources.getString(R.string.api_host_statistics),
                context.resources.getString(R.string.api_key),
                country
            )
        }
    }

}