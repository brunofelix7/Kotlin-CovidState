package com.brunofelixdev.kotlincovidstate.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.brunofelixdev.kotlincovidstate.R
import com.brunofelixdev.kotlincovidstate.adapter.MapsInfoWindowAdapter
import com.brunofelixdev.kotlincovidstate.data.api.dto.CountryLocationDto
import com.brunofelixdev.kotlincovidstate.data.api.interceptor.NetworkConnectionInterceptor
import com.brunofelixdev.kotlincovidstate.data.api.repository.CountryRepository
import com.brunofelixdev.kotlincovidstate.extension.formatNumber
import com.brunofelixdev.kotlincovidstate.extension.toast
import com.brunofelixdev.kotlincovidstate.listener.CountryLocationListener
import com.brunofelixdev.kotlincovidstate.util.APP_TAG
import com.brunofelixdev.kotlincovidstate.viewmodel.CountryViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), OnMapReadyCallback, CountryLocationListener {

    private var mapFragment: SupportMapFragment? = null
    private var viewModel: CountryViewModel? = null
    private lateinit var mMap: GoogleMap
    private lateinit var appContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                activity?.applicationContext,
                R.raw.style_json
            )
        )
        mapOptions()
        initObjects()
    }

    private fun mapOptions() {
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isZoomGesturesEnabled = true
    }

    @SuppressLint("PotentialBehaviorOverride")
    private fun addMarker(countryLocation: CountryLocationDto) {
        val country = countryLocation.country ?: "Unknown"
        val confirmed = countryLocation.confirmed ?: 0
        val deaths = countryLocation.deaths ?: 0
        val lat = countryLocation.latitude ?: 0.0
        val lng = countryLocation.longitude ?: 0.0

        mMap.setInfoWindowAdapter(activity?.applicationContext?.let { MapsInfoWindowAdapter(it) })
        val markerOptions = MarkerOptions().apply {
            position(LatLng(lat, lng))
            title(country)
            snippet("Confirmados: ${confirmed.formatNumber()}\nMortes: ${deaths.formatNumber()}")
        }
        if (country == "Brazil") {
            mMap.addMarker(markerOptions).showInfoWindow()
        } else {
            mMap.addMarker(markerOptions)
        }
    }

    private fun initObjects() {
        this.appContext = activity?.applicationContext!!
        viewModel = ViewModelProvider(
            this,
            CountryViewModel.CountryViewModelFactory(CountryRepository(NetworkConnectionInterceptor(appContext)))
        ).get(CountryViewModel::class.java)

        viewModel?.listenerCountryLocation = this
        viewModel?.listCountryLocation()
    }

    override fun onStarted() {
        //  TODO: onStarted
    }

    override fun onSuccess(data: List<CountryLocationDto>?) {
        if (data != null) {
            Log.i(APP_TAG, data.toString())
            for (item in data) {
                val country = item.country ?: "Unknown"
                val lat = item.latitude ?: 0.0
                val lng = item.longitude ?: 0.0
                val countryLocation = CountryLocationDto(country, item.confirmed, item.deaths, lat, lng)

                activity?.runOnUiThread {
                    addMarker(countryLocation)
                    if (country == "Brazil") {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 4.0f))
                    }
                }
            }
        }
    }

    override fun onError(message: String) {
        activity?.toast(message)
    }

}