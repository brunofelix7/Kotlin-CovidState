package com.brunofelixdev.kotlincovidstate.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.brunofelixdev.kotlincovidstate.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class MapsInfoWindowAdapter(private val context: Context) : GoogleMap.InfoWindowAdapter {

    private var infoWindow: View = LayoutInflater.from(context).inflate(R.layout.layout_map_info_window, null)

    private fun infoWindowConfing(marker: Marker, view: View) {
        val markerTitle = marker.title
        val markerSnippet = marker.snippet

        val title = view.findViewById<TextView>(R.id.title)
        val snippet = view.findViewById<TextView>(R.id.snippet)

        title.text = markerTitle
        snippet.text = markerSnippet
    }

    override fun getInfoWindow(marker: Marker?): View {
        infoWindowConfing(marker!!, infoWindow)
        return infoWindow
    }

    override fun getInfoContents(marker: Marker?): View {
        infoWindowConfing(marker!!, infoWindow)
        return infoWindow
    }

}