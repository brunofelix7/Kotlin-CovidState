package com.brunofelixdev.kotlincovidstate.data.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brunofelixdev.kotlincovidstate.model.WorldData
import com.brunofelixdev.kotlincovidstate.util.API_HOST
import com.brunofelixdev.kotlincovidstate.util.API_KEY
import com.brunofelixdev.kotlincovidstate.util.API_URL_WORLD
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorldDataRepository {

    fun listWorldData() : LiveData<List<WorldData>> {
        val liveData = MutableLiveData<List<WorldData>>()

        MyApi.invoke(API_URL_WORLD).fetchWorldData(API_HOST, API_KEY).enqueue(object: Callback<List<WorldData>> {
            override fun onResponse(call: Call<List<WorldData>>, response: Response<List<WorldData>>) {
                if (response.isSuccessful) {
                    liveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<WorldData>>, t: Throwable) {

            }
        })
        return liveData
    }

}