package com.brunofelixdev.kotlincovidstate.data.api.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brunofelixdev.kotlincovidstate.data.api.MyApi
import com.brunofelixdev.kotlincovidstate.model.CountryStatisticsData
import com.brunofelixdev.kotlincovidstate.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StatisticsDataRepository {

    fun fetchCountryStatistics(country: String) : LiveData<CountryStatisticsData> {
        val liveData = MutableLiveData<CountryStatisticsData>()

        MyApi.invoke(API_URL_STATISTICS).fetchCountryStatistics(API_HOST_STATISTICS, API_KEY, country).enqueue(object:
            Callback<CountryStatisticsData> {
            override fun onResponse(call: Call<CountryStatisticsData>, response: Response<CountryStatisticsData>) {
                if (response.isSuccessful) {
                    liveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<CountryStatisticsData>, t: Throwable) {

            }
        })
        return liveData
    }

}