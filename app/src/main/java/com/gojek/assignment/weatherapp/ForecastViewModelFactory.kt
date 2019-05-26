package com.gojek.assignment.weatherapp

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 * Created by Sumit on 2019-05-26.
 *
 */


class ForecastViewModelFactory(private val repository: WeatherRespository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ForecastViewModel(repository) as T
    }
}