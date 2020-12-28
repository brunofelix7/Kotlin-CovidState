package com.brunofelixdev.kotlincovidstate.data.api

import com.brunofelixdev.kotlincovidstate.data.api.interceptor.NetworkConnectionInterceptor
import com.brunofelixdev.kotlincovidstate.data.api.dto.CountryDto
import com.brunofelixdev.kotlincovidstate.data.api.dto.CountryLocationDto
import com.brunofelixdev.kotlincovidstate.data.api.dto.CountryStatisticsData
import com.brunofelixdev.kotlincovidstate.data.api.dto.WorldTotalDto
import okhttp3.OkHttpClient
import retrofit2.Response
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
    suspend fun fetchWorldData(
        @Header("x-rapidapi-host") host: String,
        @Header("x-rapidapi-key") apiKey: String
    ): Response<List<WorldTotalDto>>

    @GET("country/all")
    suspend fun fetchAllCountries(
        @Header("x-rapidapi-host") host: String,
        @Header("x-rapidapi-key") apiKey: String
    ): Response<List<CountryDto>>

    @GET("country/all")
    suspend fun fetchCountryLocation(
        @Header("x-rapidapi-host") host: String,
        @Header("x-rapidapi-key") apiKey: String
    ): Response<List<CountryLocationDto>>

    @GET("statistics")
    suspend fun fetchCountryStatistics(
        @Header("x-rapidapi-host") host: String,
        @Header("x-rapidapi-key") apiKey: String,
        @Query("country") country: String
    ): Response<CountryStatisticsData>

}