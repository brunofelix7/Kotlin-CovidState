package com.brunofelixdev.kotlincovidstate.integration.data.api

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.brunofelixdev.kotlincovidstate.data.api.interceptor.NetworkConnectionInterceptor
import com.brunofelixdev.kotlincovidstate.data.api.repository.CountryDetailsRepository
import com.brunofelixdev.kotlincovidstate.data.api.repository.CountryRepository
import com.brunofelixdev.kotlincovidstate.data.api.repository.WorldTotalRepository
import com.brunofelixdev.kotlincovidstate.util.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Testes de integracao com a API
 */
@RunWith(AndroidJUnit4::class)
class ApiTest {

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun apiWorldDataRequest_test() {
        val expected = 1

        Coroutines.main {
            val response = WorldTotalRepository(NetworkConnectionInterceptor(appContext)).fetchWorldData()

            if (response.isEmpty()) {
                assertEquals(expected, response.size)
            }
        }
    }

    @Test
    fun apiAllCountriesRequest_test() {
        val expected = 1

        Coroutines.main {
            val response = CountryRepository(NetworkConnectionInterceptor(appContext)).fetchCountries()

            if (response.isEmpty()) {
                assertEquals(expected, response.size)
            }
        }
    }

    @Test
    fun apiCountryDetailsRequest_test() {
        val expected = 1
        val country = "Brazil"

        Coroutines.main {
            val response = CountryDetailsRepository(NetworkConnectionInterceptor(appContext)).fetchCountryStatistics(country)

            assertEquals(expected, response.response?.size)
        }
    }
}