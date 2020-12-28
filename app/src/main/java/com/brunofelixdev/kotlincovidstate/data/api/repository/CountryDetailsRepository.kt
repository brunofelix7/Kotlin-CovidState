package com.brunofelixdev.kotlincovidstate.data.api.repository

import com.brunofelixdev.kotlincovidstate.data.api.Api
import com.brunofelixdev.kotlincovidstate.data.api.SafeApiRequest
import com.brunofelixdev.kotlincovidstate.data.api.interceptor.NetworkConnectionInterceptor
import com.brunofelixdev.kotlincovidstate.data.api.dto.CountryStatisticsData
import com.brunofelixdev.kotlincovidstate.util.*

class CountryDetailsRepository(private val networkConnectionInterceptor: NetworkConnectionInterceptor) : SafeApiRequest() {

    suspend fun fetchCountryStatistics(country: String) : CountryStatisticsData {
        return apiRequest { Api(API_URL_STATISTICS, networkConnectionInterceptor).fetchCountryStatistics(API_HOST_STATISTICS, API_KEY, country) }
    }

}