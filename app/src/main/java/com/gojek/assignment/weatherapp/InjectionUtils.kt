package com.gojek.assignment.weatherapp


object InjectionUtils {

    private fun getWeatherRepoSitory(): WeatherRespository {
        return WeatherRespository()
    }

    fun getLoginViewModelFactory(): ForecastViewModelFactory {
        return ForecastViewModelFactory(getWeatherRepoSitory())
    }
}