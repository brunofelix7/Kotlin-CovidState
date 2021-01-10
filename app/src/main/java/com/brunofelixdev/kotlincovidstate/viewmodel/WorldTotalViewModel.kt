package com.brunofelixdev.kotlincovidstate.viewmodel

import androidx.lifecycle.ViewModel
import com.brunofelixdev.kotlincovidstate.data.api.repository.WorldTotalRepository
import com.brunofelixdev.kotlincovidstate.handler.ApiException
import com.brunofelixdev.kotlincovidstate.handler.NoInternetException
import com.brunofelixdev.kotlincovidstate.listener.WorldTotalListener
import com.brunofelixdev.kotlincovidstate.util.Coroutines

class WorldTotalViewModel(private val repository: WorldTotalRepository) : ViewModel() {

    var listener: WorldTotalListener? = null

    fun listWorldTotal() {
        listener?.onStarted()

        Coroutines.main {
            try {
                val response = repository.fetchWorldData()

                listener?.onSuccess(response)
            } catch (e: ApiException) {
                listener?.onError(e.message ?: "")
            } catch (e: NoInternetException) {
                listener?.onError(e.message ?: "")
            }
        }
    }
}