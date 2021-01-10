package com.brunofelixdev.kotlincovidstate.di

import com.brunofelixdev.kotlincovidstate.data.api.interceptor.NetworkConnectionInterceptor
import com.brunofelixdev.kotlincovidstate.data.api.repository.WorldTotalRepository
import com.brunofelixdev.kotlincovidstate.viewmodel.WorldTotalViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    factory {
        NetworkConnectionInterceptor(context = get())
    }

    viewModel {
        WorldTotalViewModel(
            WorldTotalRepository(
                context = get(),
                networkConnectionInterceptor = get()
            )
        )
    }
}