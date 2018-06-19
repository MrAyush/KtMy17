package com.example.ayushgupta.ktmy17

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var lati: Double
        var long: Double

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000.toLong(), 1.0f, object : LocationListener {
                override fun onLocationChanged(location: Location?) {
                    lati = location?.latitude!!
                    long = location.longitude
                    val sfrag: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.frag1) as SupportMapFragment
                    sfrag.getMapAsync { googleMap ->
                        val option = MarkerOptions()
                        option.position(LatLng(lati, long))
                        option.title("Your location")
                        googleMap.clear()
                        googleMap.addMarker(option)
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lati, long), 16f))
                    }
                }

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                }

                override fun onProviderEnabled(provider: String?) {
                }

                override fun onProviderDisabled(provider: String?) {
                }
            })
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), 100)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Can't check location", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            recreate()
        }
    }
}
