package com.brunofelixdev.kotlincovidstate.data.api

import com.brunofelixdev.kotlincovidstate.model.CountryData
import com.brunofelixdev.kotlincovidstate.model.CountryStatisticsData
import com.brunofelixdev.kotlincovidstate.model.WorldData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MyApi {

    companion object {
        operator fun invoke(url: String): MyApi {
            return Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MyApi::class.java)
        }
    }

    @GET("totals")
    fun fetchWorldData(
            @Header("x-rapidapi-host") host: String,
            @Header("x-rapidapi-key") apiKey: String
    ) : Call<List<WorldData>>

    @GET("country/all")
    fun fetchAllCountries(
            @Header("x-rapidapi-host") host: String,
            @Header("x-rapidapi-key") apiKey: String
    ) : Call<List<CountryData>>

    @GET("statistics")
    fun fetchCountryStatistics(
        @Header("x-rapidapi-host") host: String,
        @Header("x-rapidapi-key") apiKey: String,
        @Query("country") country: String
    ) : Call<CountryStatisticsData>

}