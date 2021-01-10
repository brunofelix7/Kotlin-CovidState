package com.brunofelixdev.kotlincovidstate.di

import com.brunofelixdev.kotlincovidstate.data.api.interceptor.NetworkConnectionInterceptor
import com.brunofelixdev.kotlincovidstate.data.api.repository.CountryDetailsRepository
import com.brunofelixdev.kotlincovidstate.data.api.repository.CountryRepository
import com.brunofelixdev.kotlincovidstate.data.api.repository.WorldTotalRepository
import com.brunofelixdev.kotlincovidstate.fragment.MapsFragment
import com.brunofelixdev.kotlincovidstate.fragment.RecentFragment
import com.brunofelixdev.kotlincovidstate.viewmodel.CountryDetailsViewModel
import com.brunofelixdev.kotlincovidstate.viewmodel.CountryViewModel
import com.brunofelixdev.kotlincovidstate.viewmodel.WorldTotalViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Container de injecção de dependências do app
 */
val mainModule = module {

    /**  Singletons */
    single { NetworkConnectionInterceptor(context = get()) }

    /** Fragments */
    fragment { RecentFragment() }
    fragment { MapsFragment() }

    /** ViewModels */
    viewModel {
        WorldTotalViewModel(
            WorldTotalRepository(
                context = get(),
                networkConnectionInterceptor = get()
            )
        )
    }

    viewModel {
        CountryViewModel(
            CountryRepository(
                context = get(),
                networkConnectionInterceptor = get()
            )
        )
    }

    viewModel {
        CountryDetailsViewModel(
            CountryDetailsRepository(
                context = get(),
                networkConnectionInterceptor = get()
            )
        )
    }
}