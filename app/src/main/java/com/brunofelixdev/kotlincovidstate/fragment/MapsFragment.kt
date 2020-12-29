package com.brunofelixdev.kotlincovidstate.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.brunofelixdev.kotlincovidstate.R
import com.brunofelixdev.kotlincovidstate.adapter.MapsInfoWindowAdapter
import com.brunofelixdev.kotlincovidstate.data.api.dto.CountryLocationDto
import com.brunofelixdev.kotlincovidstate.data.api.interceptor.NetworkConnectionInterceptor
import com.brunofelixdev.kotlincovidstate.data.api.repository.CountryRepository
import com.brunofelixdev.kotlincovidstate.databinding.FragmentMapsBinding
import com.brunofelixdev.kotlincovidstate.extension.formatNumber
import com.brunofelixdev.kotlincovidstate.extension.toast
import com.brunofelixdev.kotlincovidstate.listener.CountryLocationListener
import com.brunofelixdev.kotlincovidstate.viewmodel.CountryViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), OnMapReadyCallback, CountryLocationListener {

    private var binding: FragmentMapsBinding? = null
    private var mapFragment: SupportMapFragment? = null
    private var viewModel: CountryViewModel? = null
    private lateinit var mMap: GoogleMap
    private lateinit var appContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_maps, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        changeStyleByUiMode()
        mapOptions()
        initObjects()
    }

    private fun changeStyleByUiMode() {
        when (isInNightMode()) {
            true -> {
                mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        activity?.applicationContext,
                        R.raw.style_night
                    )
                )
            }
        }
    }

    private fun isInNightMode() : Boolean {
        return when(resources.configuration.uiMode) {
            33 -> true
            else -> false
        }
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
        binding?.progress?.visibility = View.VISIBLE
    }

    override fun onSuccess(data: List<CountryLocationDto>?) {
        if (data != null) {
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
        binding?.progress?.visibility = View.GONE
    }

    override fun onError(message: String) {
        binding?.progress?.visibility = View.GONE
        activity?.toast(message)
    }

}