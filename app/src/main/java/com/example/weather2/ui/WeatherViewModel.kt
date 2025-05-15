package com.example.weather2.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather2.model.WeatherData
import com.example.weather2.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : ViewModel() {

    private val repository: WeatherRepository = WeatherRepository(application)
    private val _weatherData = MutableLiveData<WeatherData?>()
    val weatherData: LiveData<WeatherData?> = _weatherData

    fun loadWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                _weatherData.value = repository.getWeather(latitude, longitude)
            } catch (e: Exception) {
                _weatherData.value = null
            }
        }
    }
}