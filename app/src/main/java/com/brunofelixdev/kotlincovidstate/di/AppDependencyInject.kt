package com.brunofelixdev.kotlincovidstate.di

import android.app.Application
import com.brunofelixdev.kotlincovidstate.data.api.Api
import com.brunofelixdev.kotlincovidstate.data.api.interceptor.NetworkConnectionInterceptor
import com.brunofelixdev.kotlincovidstate.data.api.repository.CountryDetailsRepository
import com.brunofelixdev.kotlincovidstate.data.api.repository.CountryRepository
import com.brunofelixdev.kotlincovidstate.data.api.repository.WorldTotalRepository
import com.brunofelixdev.kotlincovidstate.data.db.AppDatabase
import com.brunofelixdev.kotlincovidstate.fragment.MapsFragment
import com.brunofelixdev.kotlincovidstate.fragment.RecentFragment
import com.brunofelixdev.kotlincovidstate.viewmodel.CountryDetailsViewModel.CountryDetailsViewModelFactory
import com.brunofelixdev.kotlincovidstate.viewmodel.CountryViewModel.CountryViewModelFactory
import com.brunofelixdev.kotlincovidstate.viewmodel.WorldTotalViewModel.WorldTotalViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class AppDependencyInject : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@AppDependencyInject))

        bind() from singleton { RecentFragment() }
        bind() from singleton { MapsFragment() }
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { Api("", instance()) }
        bind() from singleton { CountryRepository(instance(), instance()) }
        bind() from singleton { CountryDetailsRepository(instance(), instance()) }
        bind() from singleton { WorldTotalRepository(instance(), instance()) }
        bind() from singleton { CountryViewModelFactory(instance()) }
        bind() from singleton { CountryDetailsViewModelFactory(instance()) }
        bind() from singleton { WorldTotalViewModelFactory(instance()) }

    }

}