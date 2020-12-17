package com.brunofelixdev.kotlincovidstate.data.api

import com.brunofelixdev.kotlincovidstate.model.WorldData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

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

}