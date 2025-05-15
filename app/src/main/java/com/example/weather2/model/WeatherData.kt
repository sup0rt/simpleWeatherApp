package com.example.weather2.model

data class WeatherData(
    val city: String,
    val currentTime: String,
    val dailyForecasts: List<DailyForecast>,
    val hourlyForecasts: List<HourlyForecast>
)