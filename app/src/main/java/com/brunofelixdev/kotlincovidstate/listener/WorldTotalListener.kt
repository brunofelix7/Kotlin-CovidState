package com.brunofelixdev.kotlincovidstate.listener

import com.brunofelixdev.kotlincovidstate.data.api.dto.WorldTotalDto

interface WorldTotalListener {

    fun onStarted()
    fun onSuccess(data: List<WorldTotalDto>?)
    fun onError(message: String)

}