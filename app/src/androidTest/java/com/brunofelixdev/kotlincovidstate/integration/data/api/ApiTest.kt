package com.brunofelixdev.kotlincovidstate.integration.data.api

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.brunofelixdev.kotlincovidstate.data.api.Api
import com.brunofelixdev.kotlincovidstate.data.api.interceptor.NetworkConnectionInterceptor
import com.brunofelixdev.kotlincovidstate.handler.ApiException
import com.brunofelixdev.kotlincovidstate.model.CountryData
import com.brunofelixdev.kotlincovidstate.model.CountryStatisticsData
import com.brunofelixdev.kotlincovidstate.model.WorldData
import com.brunofelixdev.kotlincovidstate.util.*

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Testes de integracao com a API
 */
@RunWith(AndroidJUnit4::class)
class ApiTest {

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val networkConnectionInterceptor = NetworkConnectionInterceptor(appContext)

    @Test
    fun apiWorldDataRequest_test() {
        val expected = 200

        Api(API_URL, networkConnectionInterceptor).fetchWorldData(API_HOST, API_KEY).enqueue(object:
            Callback<List<WorldData>> {
            override fun onResponse(call: Call<List<WorldData>>, response: Response<List<WorldData>>) {
                val result = response.isSuccessful

                assertEquals(expected, result)
            }

            override fun onFailure(call: Call<List<WorldData>>, t: Throwable) {

            }
        })
    }

    @Test
    fun apiAllCountriesRequest_test() {
        val expected = 200

        Api(API_URL, networkConnectionInterceptor).fetchAllCountries(API_HOST, API_KEY).enqueue(object: Callback<List<CountryData>> {
            override fun onResponse(call: Call<List<CountryData>>, response: Response<List<CountryData>>) {
                val result = response.isSuccessful

                assertEquals(expected, result)
            }

            override fun onFailure(call: Call<List<CountryData>>, t: Throwable) {
                throw ApiException("Oops!!")
            }
        })
    }

    @Test
    fun apiCountryStatisticsRequest_test() {
        val expected = 200
        val country = "Brazil"

        Api(API_URL_STATISTICS, networkConnectionInterceptor).fetchCountryStatistics(
            API_HOST_STATISTICS, API_KEY, country).enqueue(object:
            Callback<CountryStatisticsData> {
            override fun onResponse(call: Call<CountryStatisticsData>, response: Response<CountryStatisticsData>) {
                val result = response.isSuccessful

                assertEquals(expected, result)
            }

            override fun onFailure(call: Call<CountryStatisticsData>, t: Throwable) {

            }
        })
    }
}