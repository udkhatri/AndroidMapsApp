package com.uday.mapsapp

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(R.layout.fragment_maps) {

    private val callback = OnMapReadyCallback { googleMap ->
        if (MainActivity.address != ""){
            val loc = MainActivity.loc
            googleMap.addMarker(
                MarkerOptions().position(loc)
                    .title("Uday Khatri " + MainActivity.address)
                    .snippet("Your location Lat:" + loc.latitude + " ,Lng:" + loc.longitude))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16f))
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }

}