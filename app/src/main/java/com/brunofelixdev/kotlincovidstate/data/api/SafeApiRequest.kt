package com.brunofelixdev.kotlincovidstate.data.api

import com.brunofelixdev.kotlincovidstate.handler.ApiException
import retrofit2.Response
import java.lang.StringBuilder

abstract class SafeApiRequest {

    suspend fun<T: Any> apiRequest(call: suspend () -> Response<T>) : T {
        val response = call.invoke()

        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val message = StringBuilder()
            val error = response.errorBody()

            with(message) {
                append("Error code: ${response.code()}")
                append("\n")
                append("Message ${error?.string()}")
            }

            throw ApiException(message.toString())
        }
    }

}