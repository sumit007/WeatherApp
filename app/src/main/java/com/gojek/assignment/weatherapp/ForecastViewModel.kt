package com.gojek.assignment.weatherapp

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel

/**
 * Created by Sumit on 2019-05-26.
 *
 */


class ForecastViewModel(private val weatherRepository: WeatherRespository) : ViewModel() {

    fun getForecast() : LiveData<WeatherForecastModel> {
        return weatherRepository.getWeatheatherForecast()
    }

    fun getNetworkStatus() : LiveData<NetworkStatus> {
        return weatherRepository.getNetworkStatus()
    }
}