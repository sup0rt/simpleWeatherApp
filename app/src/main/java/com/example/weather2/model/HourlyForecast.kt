package com.example.weather2.model

data class HourlyForecast(
    val time: String,
    val temperature: Int,
    val weatherCode: Int,
    val windSpeed: Double,
    val isDaytime: Boolean
)