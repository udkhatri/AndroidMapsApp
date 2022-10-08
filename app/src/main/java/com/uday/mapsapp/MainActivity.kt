package com.uday.mapsapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object{
        var loc: LatLng = LatLng(67.0,67.0)
        var address: String = ""
    }
    val mapFrag = MapsFragment()
    val emailFrag = EmailFragment()
    val smsFrag = SMSFragment()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }else{

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    if (location != null) {
                        loc = LatLng(location.latitude , location.longitude)
                        address = getAddress(loc)
                    }
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.FLFragment, mapFrag)
                        commit()
                    }
                }
            return
        }

    }
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result:Boolean ->
        // checking the result of permission
        if (result) {
            Toast.makeText(this,getString(R.string.per_grant), Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this,getString(R.string.per_not_grant), Toast.LENGTH_SHORT).show()
        }
    }
    private fun getAddress(loc:LatLng): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        var addresses: List<Address>? = null
        try {
            addresses = geocoder.getFromLocation(loc!!.latitude, loc!!.longitude, 1)
        } catch (e1: IOException) {
            Log.e("MAP", getString(R.string.service), e1)
        } catch (e2: IllegalArgumentException) {
            Log.e("MAP", getString(R.string.invalid_lat_long)+ ". " +
                    "Latitude = " + loc!!.latitude +
                    ", Longitude = " +
                    loc!!.longitude, e2)
        }
        // If the reverse geocode returned an address
        if (addresses != null) {
            // Get the first address
            val address = addresses[0]
            val addressText = String.format(
                "%s, %s, %s",
                address.getAddressLine(0),
                address.locality,
                address.countryName)
            return addressText
        }
        else
        {
            Log.e("MAP", getString(R.string.no_address_found))
            return ""
        }
    }
    fun onMapClick(view: View) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.FLFragment, mapFrag)
            commit()
        }
    }
    fun onSmsClick(view: View) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.FLFragment, smsFrag)
            commit()
        }
    }
    fun onEmailClick(view: View) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.FLFragment, emailFrag)
            commit()
        }
    }
}