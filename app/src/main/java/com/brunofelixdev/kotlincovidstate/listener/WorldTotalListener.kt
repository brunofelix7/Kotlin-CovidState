package com.brunofelixdev.kotlincovidstate.listener

import com.brunofelixdev.kotlincovidstate.data.api.response.WorldTotalResponse

interface WorldTotalListener {

    fun onStarted()
    fun onSuccess(data: List<WorldTotalResponse>?)
    fun onError(message: String)

}