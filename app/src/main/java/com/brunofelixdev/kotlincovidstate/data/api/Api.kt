package com.brunofelixdev.kotlincovidstate.data.api

import com.brunofelixdev.kotlincovidstate.data.api.interceptor.NetworkConnectionInterceptor
import com.brunofelixdev.kotlincovidstate.model.CountryData
import com.brunofelixdev.kotlincovidstate.model.CountryStatisticsData
import com.brunofelixdev.kotlincovidstate.model.WorldData
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface Api {

    /** Realiza a conexao com a API */
    companion object {
        operator fun invoke(
            url: String,
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): Api {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }

    @GET("totals")
    fun fetchWorldData(
        @Header("x-rapidapi-host") host: String,
        @Header("x-rapidapi-key") apiKey: String
    ): Call<List<WorldData>>

    @GET("country/all")
    fun fetchAllCountries(
        @Header("x-rapidapi-host") host: String,
        @Header("x-rapidapi-key") apiKey: String
    ): Call<List<CountryData>>

    @GET("statistics")
    fun fetchCountryStatistics(
        @Header("x-rapidapi-host") host: String,
        @Header("x-rapidapi-key") apiKey: String,
        @Query("country") country: String
    ): Call<CountryStatisticsData>

}