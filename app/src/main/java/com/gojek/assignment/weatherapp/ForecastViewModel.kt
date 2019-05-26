package com.gojek.assignment.weatherapp

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * Created by Sumit on 2019-05-26.
 *
 */


class ForecastViewModel(private val weatherRepository: WeatherRespository) : ViewModel() {

    fun getForecast(latLng: String) : MutableLiveData<WeatherForecastModel> {
        return weatherRepository.getWeatheatherForecast(latLng)
    }

    fun getNetworkStatus() : LiveData<NetworkStatus> {
        return weatherRepository.getNetworkStatus()
    }
}