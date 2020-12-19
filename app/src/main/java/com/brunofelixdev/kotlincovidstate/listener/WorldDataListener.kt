package com.brunofelixdev.kotlincovidstate.listener

import androidx.lifecycle.LiveData
import com.brunofelixdev.kotlincovidstate.model.WorldData

interface WorldDataListener {

    fun onStarted()
    fun onCompleted(liveData: LiveData<List<WorldData>>)

}