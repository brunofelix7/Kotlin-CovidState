package com.brunofelixdev.kotlincovidstate.data.api

import com.brunofelixdev.kotlincovidstate.model.WorldData
import com.brunofelixdev.kotlincovidstate.util.API_HOST
import com.brunofelixdev.kotlincovidstate.util.API_KEY
import com.brunofelixdev.kotlincovidstate.util.API_URL_WORLD
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyApiUnitTest {

    @Test
    fun apiConnect_test() {
        val expected = 200

        MyApi.invoke(API_URL_WORLD).fetchWorldData(API_HOST, API_KEY).enqueue(object: Callback<List<WorldData>> {
            override fun onResponse(call: Call<List<WorldData>>, response: Response<List<WorldData>>) {
                val result = response.isSuccessful

                assertEquals(expected, result)
            }

            override fun onFailure(call: Call<List<WorldData>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}