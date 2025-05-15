package com.example.weather2.repository

import android.content.Context
import com.example.weather2.model.WeatherData
import com.example.weather2.utils.LocationHelper
import com.example.weather2.model.DailyForecast
import com.example.weather2.model.HourlyForecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class WeatherRepository(private val context: Context) {
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    suspend fun getWeather(lat: Double, lon: Double): WeatherData {
        return withContext(Dispatchers.IO) {
            val url = buildApiUrl(lat, lon)
            val json = URL(url).readText()
            parseWeatherData(json, lat, lon)
        }
    }

    private fun getCurrentIsoTime(): String {
        val now = Calendar.getInstance()
        now.set(Calendar.MINUTE, 0)
        now.set(Calendar.SECOND, 0)
        return SimpleDateFormat("yyyy-MM-dd'T'HH:00", Locale.getDefault()).format(now.time)
    }

    private fun buildApiUrl(lat: Double, lon: Double): String {
        return "https://api.open-meteo.com/v1/forecast?" +
                "latitude=$lat&longitude=$lon" +
                "&daily=weather_code,temperature_2m_min,temperature_2m_max,wind_speed_10m_max" +
                "&hourly=temperature_2m,weather_code,wind_speed_10m" +
                "&current_weather=true" +
                "&timezone=auto" +
                "&forecast_days=7"
    }


    private fun parseWeatherData(json: String, lat: Double, lon: Double): WeatherData {
        val root = JSONObject(json)
        val daily = root.getJSONObject("daily")
        val hourly = root.getJSONObject("hourly")
        val locationHelper = LocationHelper(context)
        val city = locationHelper.getCityName(lat, lon) ?: "Неизвестно"
        val currentTime = timeFormat.format(Date())

        val dailyForecasts = mutableListOf<DailyForecast>()
        for (i in 0 until daily.getJSONArray("time").length()) {
            dailyForecasts.add(
                DailyForecast(
                    date = daily.getJSONArray("time").getString(i),
                    weatherCode = daily.getJSONArray("weather_code").getInt(i),
                    tempMin = daily.getJSONArray("temperature_2m_min").getDouble(i).toInt(),
                    tempMax = daily.getJSONArray("temperature_2m_max").getDouble(i).toInt(),
                    windSpeed = daily.getJSONArray("wind_speed_10m_max").getDouble(i),
                    isDay = true
                )
            )
        }

        val hourlyForecasts = mutableListOf<HourlyForecast>()
        val hourlyTimes = hourly.getJSONArray("time")
        val currentTimeIso = getCurrentIsoTime()

        var startIndex = 0
        for (i in 0 until hourlyTimes.length()) {
            if (hourlyTimes.getString(i) == currentTimeIso) {
                startIndex = i
                break
            }
        }

        val endIndex = minOf(startIndex + 24, hourlyTimes.length())
        for (i in startIndex until endIndex) {
            hourlyForecasts.add(
                HourlyForecast(
                    time = hourlyTimes.getString(i).split("T")[1].substring(0, 5),
                    temperature = hourly.getJSONArray("temperature_2m").getDouble(i).toInt(),
                    weatherCode = hourly.getJSONArray("weather_code").getInt(i),
                    windSpeed = hourly.getJSONArray("wind_speed_10m").getDouble(i),
                    isDaytime = isDayTime(hourlyTimes.getString(i))
                )
            )
        }
        return WeatherData(city, currentTime, dailyForecasts, hourlyForecasts)
    }

    private fun isDayTime(timeString: String): Boolean {
        val hour = timeString.split("T")[1].split(":")[0].toInt()
        return hour in 6..20
    }
}