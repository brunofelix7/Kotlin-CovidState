package com.brunofelixdev.kotlincovidstate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brunofelixdev.kotlincovidstate.data.api.WorldDataRepository
import com.brunofelixdev.kotlincovidstate.listener.WorldDataListener

class WorldDataViewModel(private val repository: WorldDataRepository) : ViewModel() {

    var listener: WorldDataListener? = null

    fun listWorldData() {
        listener?.onStarted()
        listener?.onCompleted(repository.listWorldData())
    }

    class WorldDataViewModelFactory(private val repository: WorldDataRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return WorldDataViewModel(repository) as T
        }
    }

}