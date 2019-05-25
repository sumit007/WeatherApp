package com.gojek.assignment.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener



class MainActivity : AppCompatActivity() {

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var mLocation : Location? = null;


    companion object {
        private const val LOCATION_PERMISSION_REQUEST = 10001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        //actionBar.visi

        getLastLocation()
    }

    private fun getLastLocation() {

        if (ContextCompat.checkSelfPermission(this@MainActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient?.lastLocation
                ?.addOnSuccessListener(this) { location ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        mLocation = location

                    }
                }

        } else {
            checkPermissions()
        }
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this@MainActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                showRationaleDialog(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))

            } else {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    LOCATION_PERMISSION_REQUEST)

            }
        } else {
            getLastLocation()
        }

    }

    private fun showRationaleDialog(permissions: Array<out String>) {
        val message = StringBuilder("The app has not been granted permissions:\n\n")
        for (permission in permissions) {
            message.append(permission)
            message.append("\n")
        }
        message.append("\nHence, it cannot function properly." + "\nPlease consider granting it this permission in phone Settings.")

        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle(R.string.permission_required)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { dialogInterface, i ->
                dialogInterface.dismiss()
                this@MainActivity.finish()
            }
        val alert = builder.create()
        alert.show()
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getLastLocation()

                } else {
                    this@MainActivity.finish()

                }
                return
            }

        }
    }
}
