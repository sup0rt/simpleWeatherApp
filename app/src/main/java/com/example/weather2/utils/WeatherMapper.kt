package com.example.weather2.utils

import com.example.weather2.R

object WeatherMapper {
    fun codeToDescription(code: Int): String {
        return when(code) {
            0 -> "Ясно"
            in 1..2 -> "Переменная облачность"
            3 -> "Пасмурно"
            in 45..48 -> "Туман"
            in 51..57 -> "Морось"
            in 61..65 -> "Дождь"
            in 66..67 -> "Ледяной дождь"
            in 71..75 -> "Снег"
            in 77..79 -> "Снежные зерна"
            in 80..82 -> "Ливень"
            in 85..86 -> "Снегопад"
            in 95..99 -> "Гроза"
            else -> "Облачно"
        }
    }

    fun codeToIcon(code: Int, isDay: Boolean): Int {
        return when(code) {
            0 -> if (isDay) R.drawable.ic_sunny else R.drawable.ic_clear_night
            in 1..2 -> if (isDay) R.drawable.ic_partly_cloudy_day else R.drawable.ic_partly_cloudy_night
            3 -> R.drawable.ic_cloudy
            in 45..48 -> R.drawable.ic_fog
            in 51..57 -> R.drawable.ic_drizzle
            in 61..65 -> R.drawable.ic_rain
            in 66..67 -> R.drawable.ic_freezing_rain
            in 71..75 -> R.drawable.ic_snow
            in 77..79 -> R.drawable.ic_snow_grains
            in 80..82 -> R.drawable.ic_heavy_rain
            in 85..86 -> R.drawable.ic_heavy_snow
            in 95..99 -> R.drawable.ic_thunderstorm
            else -> R.drawable.ic_unknown
        }
    }
}