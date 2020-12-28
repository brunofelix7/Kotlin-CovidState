package com.brunofelixdev.kotlincovidstate.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.NetworkOnMainThreadException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brunofelixdev.kotlincovidstate.R
import com.brunofelixdev.kotlincovidstate.adapter.MapsInfoWindowAdapter
import com.brunofelixdev.kotlincovidstate.data.api.interceptor.NetworkConnectionInterceptor
import com.brunofelixdev.kotlincovidstate.data.api.repository.DataRepository
import com.brunofelixdev.kotlincovidstate.extension.formatNumber
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), OnMapReadyCallback {

    private var mapFragment: SupportMapFragment? = null
    private lateinit var mMap: GoogleMap
    private lateinit var repository: DataRepository
    private lateinit var appContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initObjects()
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.addMarker(MarkerOptions().position(LatLng(-34.0, 151.0)).title("Marker in Sydney"))
        mMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                activity?.applicationContext,
                R.raw.style_json
            )
        )
        mapOptions()
        getCountriesLocation()
    }

    private fun mapOptions() {
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isZoomGesturesEnabled = true
    }

    private fun getCountriesLocation() {
        repository.fetchAllCountries().observe(this, { data ->
            if (data != null) {
                for (item in data) {
                    val country = item.country ?: "Unknown"
                    val confirmed = item.confirmed ?: 0
                    val deaths = item.deaths ?: 0
                    val lat = item.latitude ?: 0.0
                    val lng = item.longitude ?: 0.0
                    activity?.runOnUiThread { addMarker(country, confirmed, deaths, LatLng(lat, lng))
                        if (country == "Brazil") {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 4.0f))
                        }
                    }
                }
            }
        })
    }

    @SuppressLint("PotentialBehaviorOverride")
    private fun addMarker(country: String, confirmed: Long, deaths: Long, location: LatLng) {
        mMap.setInfoWindowAdapter(activity?.applicationContext?.let { MapsInfoWindowAdapter(it) })
        val markerOptions = MarkerOptions().apply {
            position(location)
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
        this.repository = DataRepository(appContext, NetworkConnectionInterceptor(appContext))
    }

}