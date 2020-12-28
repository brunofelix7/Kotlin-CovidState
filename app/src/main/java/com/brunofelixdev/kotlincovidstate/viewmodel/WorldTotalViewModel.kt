package com.brunofelixdev.kotlincovidstate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brunofelixdev.kotlincovidstate.data.api.repository.WorldTotalRepository
import com.brunofelixdev.kotlincovidstate.handler.NoInternetException
import com.brunofelixdev.kotlincovidstate.listener.WorldTotalListener
import com.brunofelixdev.kotlincovidstate.util.Coroutines
import com.google.android.gms.common.api.ApiException

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

    class WorldTotalViewModelFactory(private val repository: WorldTotalRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return WorldTotalViewModel(repository) as T
        }
    }

}