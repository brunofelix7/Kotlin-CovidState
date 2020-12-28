package com.brunofelixdev.kotlincovidstate.data.api.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brunofelixdev.kotlincovidstate.data.api.Api
import com.brunofelixdev.kotlincovidstate.data.api.interceptor.NetworkConnectionInterceptor
import com.brunofelixdev.kotlincovidstate.model.CountryStatisticsData
import com.brunofelixdev.kotlincovidstate.util.API_HOST_STATISTICS
import com.brunofelixdev.kotlincovidstate.util.API_KEY
import com.brunofelixdev.kotlincovidstate.util.API_URL_STATISTICS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StatisticsDataRepository(private val context: Context, private val networkConnectionInterceptor: NetworkConnectionInterceptor) {

    fun fetchCountryStatistics(country: String) : LiveData<CountryStatisticsData> {
        val liveData = MutableLiveData<CountryStatisticsData>()

        Api(API_URL_STATISTICS, networkConnectionInterceptor).fetchCountryStatistics(API_HOST_STATISTICS, API_KEY, country).enqueue(object:
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