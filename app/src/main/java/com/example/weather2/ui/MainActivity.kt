package com.example.weather2.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather2.databinding.ActivityMainBinding
import com.example.weather2.utils.LocationHelper
import com.example.weather2.ui.adapters.DailyAdapter
import com.example.weather2.ui.adapters.HourlyAdapter
import com.example.weather2.ui.view.ThemeManager
import com.example.weather2.ui.view.WeatherViewModelFactory
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(application)
    }
    private lateinit var hourlyAdapter: HourlyAdapter
    private lateinit var dailyAdapter: DailyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapters()
        setupObservers()
        checkDayNightMode()
        loadWeatherData()
    }

    private fun setupAdapters() {
        hourlyAdapter = HourlyAdapter()
        binding.rvHourlyForecast.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = hourlyAdapter
        }

        dailyAdapter = DailyAdapter()
        binding.rvDailyForecast.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = dailyAdapter
        }
    }

    private fun setupObservers() {
        viewModel.weatherData.observe(this, Observer { weather ->
            weather?.let {
                binding.tvCity.text = it.city
                binding.tvCurrentTime.text = it.currentTime
                hourlyAdapter.submitList(it.hourlyForecasts.take(24))
                dailyAdapter.submitList(it.dailyForecasts)
            }
        })
    }

    private fun checkDayNightMode() {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val isNight = currentHour < 6 || currentHour > 20
        ThemeManager.applyTheme(isNight)
    }

    private fun loadWeatherData() {
        val locationHelper = LocationHelper(this)
        val location = locationHelper.getLastKnownLocation()
        location?.let {
            viewModel.loadWeather(it.latitude, it.longitude)
        }
    }
}