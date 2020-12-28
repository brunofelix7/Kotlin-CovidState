package com.brunofelixdev.kotlincovidstate.data.api.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brunofelixdev.kotlincovidstate.data.api.Api
import com.brunofelixdev.kotlincovidstate.data.api.interceptor.NetworkConnectionInterceptor
import com.brunofelixdev.kotlincovidstate.model.CountryData
import com.brunofelixdev.kotlincovidstate.model.WorldData
import com.brunofelixdev.kotlincovidstate.util.API_HOST
import com.brunofelixdev.kotlincovidstate.util.API_KEY
import com.brunofelixdev.kotlincovidstate.util.API_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataRepository(private val context: Context, private val networkConnectionInterceptor: NetworkConnectionInterceptor) {

    fun fetchWorldData(): LiveData<List<WorldData>> {
        val liveData = MutableLiveData<List<WorldData>>()

        Api(API_URL, networkConnectionInterceptor).fetchWorldData(API_HOST, API_KEY).enqueue(object: Callback<List<WorldData>> {
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

    fun fetchAllCountries(): LiveData<List<CountryData>> {
        val liveData = MutableLiveData<List<CountryData>>()

        Api(API_URL, networkConnectionInterceptor).fetchAllCountries(API_HOST, API_KEY).enqueue(object: Callback<List<CountryData>> {
            override fun onResponse(call: Call<List<CountryData>>, response: Response<List<CountryData>>) {
                if (response.isSuccessful) {
                    liveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<CountryData>>, t: Throwable) {

            }
        })
        return liveData
    }

}