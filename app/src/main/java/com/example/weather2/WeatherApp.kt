package com.example.weather2

import android.app.Application
import com.example.weather2.ui.view.ThemeManager
import java.util.Calendar

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        ThemeManager.applyTheme(currentHour < 6 || currentHour > 18)
    }
}
