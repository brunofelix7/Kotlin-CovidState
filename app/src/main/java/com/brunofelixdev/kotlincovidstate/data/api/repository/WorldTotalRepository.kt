package com.brunofelixdev.kotlincovidstate.data.api.repository

import android.content.Context
import com.brunofelixdev.kotlincovidstate.R
import com.brunofelixdev.kotlincovidstate.data.api.Api
import com.brunofelixdev.kotlincovidstate.data.api.SafeApiRequest
import com.brunofelixdev.kotlincovidstate.data.api.interceptor.NetworkConnectionInterceptor
import com.brunofelixdev.kotlincovidstate.data.api.response.WorldTotalResponse

class WorldTotalRepository(
    private val context: Context,
    private val networkConnectionInterceptor: NetworkConnectionInterceptor
) : SafeApiRequest() {

    suspend fun fetchWorldData(): List<WorldTotalResponse> {
        return apiRequest {
            Api(
                context.resources.getString(R.string.api_url),
                networkConnectionInterceptor
            ).fetchWorldData(
                context.resources.getString(R.string.api_host),
                context.resources.getString(R.string.api_key)
            )
        }
    }

}