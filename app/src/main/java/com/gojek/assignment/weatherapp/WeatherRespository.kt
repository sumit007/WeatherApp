package com.gojek.assignment.weatherapp

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.apixu.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherForecastApi = retrofit.create(WeatherForecastApi::class.java)
    }

    fun getWeatheatherForecast(latLng: String) : MutableLiveData<WeatherForecastModel> {
        networkStatus.postValue(NetworkStatus.LOADING)
        val call = weatherForecastApi.forecastWeather("5f7cbf27b5684e99a8c72423181912", latLng, 4)
        call.enqueue(this)
        return weatherForecast
    }

    fun getNetworkStatus() : MutableLiveData<NetworkStatus> {
        return networkStatus
    }

}