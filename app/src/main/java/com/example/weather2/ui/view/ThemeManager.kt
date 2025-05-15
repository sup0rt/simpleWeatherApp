package com.example.weather2.ui.view

import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {
    fun applyTheme(isNightMode: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}