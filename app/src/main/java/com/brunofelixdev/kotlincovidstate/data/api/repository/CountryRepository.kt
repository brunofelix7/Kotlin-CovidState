package com.brunofelixdev.kotlincovidstate.data.api.repository

import com.brunofelixdev.kotlincovidstate.data.api.Api
import com.brunofelixdev.kotlincovidstate.data.api.SafeApiRequest
import com.brunofelixdev.kotlincovidstate.data.api.dto.CountryDto
import com.brunofelixdev.kotlincovidstate.data.api.dto.CountryLocationDto
import com.brunofelixdev.kotlincovidstate.data.api.interceptor.NetworkConnectionInterceptor
import com.brunofelixdev.kotlincovidstate.util.API_HOST
import com.brunofelixdev.kotlincovidstate.util.API_KEY
import com.brunofelixdev.kotlincovidstate.util.API_URL

class CountryRepository(private val networkConnectionInterceptor: NetworkConnectionInterceptor) : SafeApiRequest() {

    suspend fun fetchCountries(): List<CountryDto> {
        return apiRequest { Api(API_URL, networkConnectionInterceptor).fetchAllCountries(API_HOST, API_KEY) }
    }

    suspend fun fetchCountryLocation(): List<CountryLocationDto> {
        return apiRequest { Api(API_URL, networkConnectionInterceptor).fetchCountryLocation(API_HOST, API_KEY) }
    }

}