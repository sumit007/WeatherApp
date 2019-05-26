package com.gojek.assignment.weatherapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Sumit on 2019-05-25.
 *
 */


class WeatherRespository {

    private val weatherForecastApi: WeatherForecastApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.reddit.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherForecastApi = retrofit.create(WeatherForecastApi::class.java)
    }

    fun getWeatheatherForecast() : Call<WeatherForecastModel> {
        return weatherForecastApi.forecastWeather("5f7cbf27b5684e99a8c72423181912", "12.98,77", 4)
    }

}