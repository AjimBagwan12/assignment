// ProfileActivity.kt

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ProfileActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    val textViewUserInfo: TextView = findViewById(R.id.textViewUserInfo)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Retrieve user information from intent or wherever it is stored
        val userInfo = intent.getStringExtra("USER_INFO") ?: "Default User"

        // Display user information in TextView
        textViewUserInfo.text = userInfo


        // Initialize the map fragment
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker at a sample location (replace with actual user location)
        val userLocation = LatLng(37.7749, -122.4194)
        mMap.addMarker(MarkerOptions().position(userLocation).title("User Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
    }
}
