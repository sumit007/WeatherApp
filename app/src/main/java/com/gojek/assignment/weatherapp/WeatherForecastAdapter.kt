package com.gojek.assignment.weatherapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by Sumit on 2019-05-25.
 *
 */


class WeatherForecastAdapter(private val forecastDays: List<WeatherForecastModel.Forecast.Forecastday>) : RecyclerView.Adapter<WeatherForecastAdapter.WeatherForecastHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): WeatherForecastHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.forescast_item_layout, viewGroup, false)
        return WeatherForecastHolder(itemView)
    }

    override fun getItemCount(): Int {
        return forecastDays.size
    }

    override fun onBindViewHolder(weatherHolder: WeatherForecastHolder, itemId: Int) {

        val forecastday = forecastDays[itemId]
        weatherHolder.dayNameTv?.text = forecastday.getDayOfForecast()
        weatherHolder.temperatureTv?.text = weatherHolder.temperatureTv?.context?.
            getString(R.string.degree_celcious, forecastday.day.avgtempC)
    }

    class WeatherForecastHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var dayNameTv : TextView? = null
        var temperatureTv : TextView? = null

        init {
            dayNameTv = itemView.findViewById(R.id.day_forecast_tv)
            temperatureTv = itemView.findViewById(R.id.temp_forecast_tv)
        }


    }
}