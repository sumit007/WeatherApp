package com.gojek.assignment.weatherapp

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.weather_forecast_layout.*
import android.view.animation.AnimationUtils
import android.support.v7.widget.DividerItemDecoration
import android.view.animation.TranslateAnimation


/**
 * Created by Sumit on 2019-05-25.
 *
 */


class MainActivity : AppCompatActivity() {

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private lateinit var forecastViewModel: ForecastViewModel


    companion object {
        private const val LOCATION_PERMISSION_REQUEST = 10001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()

        forecastViewModel = ViewModelProviders.of(this, InjectionUtils.getLoginViewModelFactory()).get(ForecastViewModel::class.java)

        forecastViewModel.getNetworkStatus().observe(this, Observer {
            when (it) {
                NetworkStatus.ERROR -> {
                    showError()
                }
                NetworkStatus.LOADING -> {
                    showLoading()
                }
                else -> {
                    Log.d("MainActivity", "Network request success")
                    showForecast()
                }
            }
        })

        forecast_weather_rv.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout

        }

        forecast_weather_rv.visibility = View.GONE

        checkPermissions()
    }

    private fun forecastWeather(location: Location) {

        val latLng = "" + location.latitude + "," + location.longitude

        forecastViewModel.getForecast(latLng).observe(this, Observer<WeatherForecastModel> { weatherForecast ->
            Log.i("MainActivity", "string $weatherForecast")

            if (weatherForecast != null) {
                showWeatherForecast(weatherForecast)
                setForecastAdapter(weatherForecast)

            } else {
                showError()

            }

        })
    }

    private fun showError() {
        forecast_view.visibility = View.GONE
        progress_view.visibility = View.GONE
        error_view.visibility = View.VISIBLE
    }

    private fun showLoading() {
        forecast_view.visibility = View.GONE
        progress_view.visibility = View.VISIBLE
        error_view.visibility = View.GONE
    }

    private fun showForecast() {
        forecast_view.visibility = View.VISIBLE
        progress_view.visibility = View.GONE
        error_view.visibility = View.GONE
    }

    private fun showWeatherForecast(weatherForecastModel: WeatherForecastModel) {

        today_temp_tv.text = getString(R.string.degree_celcious, weatherForecastModel.current.tempC)
        city_tv.text = weatherForecastModel.location.name

    }

    private fun setForecastAdapter(weatherForecastModel: WeatherForecastModel) {

        val animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        forecast_weather_rv.layoutAnimation = animation

        val decoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        forecast_weather_rv.addItemDecoration(decoration)

        val weatherForecastAdapter = WeatherForecastAdapter(weatherForecastModel.forecast.forecastday)
        forecast_weather_rv.adapter = weatherForecastAdapter

        forecast_weather_rv.visibility = View.VISIBLE

        val animate = TranslateAnimation(
            0f,
            0f,
            forecast_weather_rv.height.toFloat(),
            0f
        )
        animate.duration = 1500
        animate.fillAfter = true
        forecast_weather_rv.startAnimation(animate)

    }

    private fun getLastLocation() {

        if (ContextCompat.checkSelfPermission(this@MainActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient?.lastLocation
                ?.addOnSuccessListener(this) { location ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        forecastWeather(location)

                    }
                }

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
