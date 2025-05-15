package com.example.weather2.model

data class DailyForecast(
    val date: String,
    val weatherCode: Int,
    val tempMin: Int,
    val tempMax: Int,
    val windSpeed: Double,
    val isDay: Boolean
)