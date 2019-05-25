package com.gojek.assignment.weatherapp


import com.google.gson.annotations.SerializedName
import java.text.DateFormatSymbols
import java.util.*

data class WeatherForecastModel(
    @SerializedName("current")
    val current: Current,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("location")
    val location: Location
) {
    data class Forecast(
        @SerializedName("forecastday")
        val forecastday: List<Forecastday>
    ) {
        data class Forecastday(
            @SerializedName("astro")
            val astro: Astro,
            @SerializedName("date")
            val date: String,
            @SerializedName("date_epoch")
            val dateEpoch: Long,
            @SerializedName("day")
            val day: Day
        ) {
            data class Day(
                @SerializedName("avghumidity")
                val avghumidity: Double,
                @SerializedName("avgtemp_c")
                val avgtempC: Double,
                @SerializedName("avgtemp_f")
                val avgtempF: Double,
                @SerializedName("avgvis_km")
                val avgvisKm: Double,
                @SerializedName("avgvis_miles")
                val avgvisMiles: Double,
                @SerializedName("condition")
                val condition: Condition,
                @SerializedName("maxtemp_c")
                val maxtempC: Double,
                @SerializedName("maxtemp_f")
                val maxtempF: Double,
                @SerializedName("maxwind_kph")
                val maxwindKph: Double,
                @SerializedName("maxwind_mph")
                val maxwindMph: Double,
                @SerializedName("mintemp_c")
                val mintempC: Double,
                @SerializedName("mintemp_f")
                val mintempF: Double,
                @SerializedName("totalprecip_in")
                val totalprecipIn: Double,
                @SerializedName("totalprecip_mm")
                val totalprecipMm: Double,
                @SerializedName("uv")
                val uv: Double
            ) {
                data class Condition(
                    @SerializedName("code")
                    val code: Int,
                    @SerializedName("icon")
                    val icon: String,
                    @SerializedName("text")
                    val text: String
                )
            }

            data class Astro(
                @SerializedName("moonrise")
                val moonrise: String,
                @SerializedName("moonset")
                val moonset: String,
                @SerializedName("sunrise")
                val sunrise: String,
                @SerializedName("sunset")
                val sunset: String
            )

            fun getDayOfForecast() : String {
                val dayNames = DateFormatSymbols().weekdays
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = dateEpoch
                return dayNames[calendar.get(Calendar.DAY_OF_WEEK)]
            }
        }
    }

    data class Location(
        @SerializedName("country")
        val country: String,
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("localtime")
        val localtime: String,
        @SerializedName("localtime_epoch")
        val localtimeEpoch: Int,
        @SerializedName("lon")
        val lon: Double,
        @SerializedName("name")
        val name: String,
        @SerializedName("region")
        val region: String,
        @SerializedName("tz_id")
        val tzId: String
    )

    data class Current(
        @SerializedName("cloud")
        val cloud: Int,
        @SerializedName("condition")
        val condition: Condition,
        @SerializedName("feelslike_c")
        val feelslikeC: Double,
        @SerializedName("feelslike_f")
        val feelslikeF: Double,
        @SerializedName("gust_kph")
        val gustKph: Double,
        @SerializedName("gust_mph")
        val gustMph: Double,
        @SerializedName("humidity")
        val humidity: Int,
        @SerializedName("is_day")
        val isDay: Int,
        @SerializedName("last_updated")
        val lastUpdated: String,
        @SerializedName("last_updated_epoch")
        val lastUpdatedEpoch: Int,
        @SerializedName("precip_in")
        val precipIn: Double,
        @SerializedName("precip_mm")
        val precipMm: Double,
        @SerializedName("pressure_in")
        val pressureIn: Double,
        @SerializedName("pressure_mb")
        val pressureMb: Double,
        @SerializedName("temp_c")
        val tempC: Double,
        @SerializedName("temp_f")
        val tempF: Double,
        @SerializedName("uv")
        val uv: Double,
        @SerializedName("vis_km")
        val visKm: Double,
        @SerializedName("vis_miles")
        val visMiles: Double,
        @SerializedName("wind_degree")
        val windDegree: Int,
        @SerializedName("wind_dir")
        val windDir: String,
        @SerializedName("wind_kph")
        val windKph: Double,
        @SerializedName("wind_mph")
        val windMph: Double
    ) {
        data class Condition(
            @SerializedName("code")
            val code: Int,
            @SerializedName("icon")
            val icon: String,
            @SerializedName("text")
            val text: String
        )
    }
}