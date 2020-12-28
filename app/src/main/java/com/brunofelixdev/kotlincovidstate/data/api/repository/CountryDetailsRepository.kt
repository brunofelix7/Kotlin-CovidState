package com.brunofelixdev.kotlincovidstate.data.api.repository

import com.brunofelixdev.kotlincovidstate.data.api.Api
import com.brunofelixdev.kotlincovidstate.data.api.interceptor.NetworkConnectionInterceptor
import com.brunofelixdev.kotlincovidstate.data.api.dto.CountryStatisticsData
import com.brunofelixdev.kotlincovidstate.util.API_HOST_STATISTICS
import com.brunofelixdev.kotlincovidstate.util.API_KEY
import com.brunofelixdev.kotlincovidstate.util.API_URL_STATISTICS
import retrofit2.Response

class CountryDetailsRepository(private val networkConnectionInterceptor: NetworkConnectionInterceptor) {

    suspend fun fetchCountryStatistics(country: String) : Response<CountryStatisticsData> {
        return Api(API_URL_STATISTICS, networkConnectionInterceptor).fetchCountryStatistics(API_HOST_STATISTICS, API_KEY, country)
    }

}