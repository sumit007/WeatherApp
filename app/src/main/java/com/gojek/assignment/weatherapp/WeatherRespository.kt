package com.gojek.assignment.weatherapp

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Sumit on 2019-05-25.
 *
 */


class WeatherRespository : Callback<WeatherForecastModel> {

    private val weatherForecast = MutableLiveData<WeatherForecastModel>()
    private val networkStatus = MutableLiveData<NetworkStatus>()

    override fun onFailure(call: Call<WeatherForecastModel>, t: Throwable) {
        Log.d("WeatherRespository", "error")
        networkStatus.postValue(NetworkStatus.ERROR)
    }

    override fun onResponse(call: Call<WeatherForecastModel>, response: Response<WeatherForecastModel>) {
        if (response.isSuccessful) {
            networkStatus.postValue(NetworkStatus.SUCCESS)
            weatherForecast.postValue(response.body())

        } else {
            networkStatus.postValue(NetworkStatus.ERROR)
        }
    }

    private val weatherForecastApi: WeatherForecastApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.reddit.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherForecastApi = retrofit.create(WeatherForecastApi::class.java)
    }

    fun getWeatheatherForecast() : LiveData<WeatherForecastModel> {
        weatherForecastApi.forecastWeather("5f7cbf27b5684e99a8c72423181912", "12.98,77", 4)
        return weatherForecast
    }

    fun getNetworkStatus() : LiveData<NetworkStatus> {
        return networkStatus
    }

}