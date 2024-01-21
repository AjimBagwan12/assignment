package com.example.assignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

// com.example.assignment.MapsActivity.kt

// Import necessary libraries

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker at the user's location (retrieve from Firebase or use a sample location)
        val userLocation = LatLng(37.7749, -122.4194)
        mMap.addMarker(MarkerOptions().position(userLocation).title("User Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation))
    }
}
