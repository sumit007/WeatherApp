package com.gojek.assignment.weatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Sumit on 2019-05-25.
 *
 */


interface WeatherForecastApi {

    @GET("forecast.json")
    fun forecastWeather(
        @Query("key") key: String,
        @Query("q") latLng: String,
        @Query("days") days: Int
    ) : Call<WeatherForecastModel>

}