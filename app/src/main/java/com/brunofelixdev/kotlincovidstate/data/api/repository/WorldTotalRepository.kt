package com.brunofelixdev.kotlincovidstate.data.api.repository

import com.brunofelixdev.kotlincovidstate.data.api.Api
import com.brunofelixdev.kotlincovidstate.data.api.SafeApiRequest
import com.brunofelixdev.kotlincovidstate.data.api.interceptor.NetworkConnectionInterceptor
import com.brunofelixdev.kotlincovidstate.data.api.dto.WorldTotalDto
import com.brunofelixdev.kotlincovidstate.util.API_HOST
import com.brunofelixdev.kotlincovidstate.util.API_KEY
import com.brunofelixdev.kotlincovidstate.util.API_URL
import retrofit2.Response

class WorldTotalRepository(private val networkConnectionInterceptor: NetworkConnectionInterceptor) : SafeApiRequest() {

    suspend fun fetchWorldData(): List<WorldTotalDto> {
        return apiRequest { Api(API_URL, networkConnectionInterceptor).fetchWorldData(API_HOST, API_KEY) }
    }

}