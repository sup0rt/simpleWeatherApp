package com.example.weather2.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather2.model.HourlyForecast
import com.example.weather2.databinding.ItemHourlyBinding
import com.example.weather2.utils.WeatherMapper

class HourlyAdapter : RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {
    private var items = emptyList<HourlyForecast>()

    inner class ViewHolder(val binding: ItemHourlyBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHourlyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            tvTime.text = item.time
            tvTemp.text = "${item.temperature}°"
            tvWind.text = "${item.windSpeed} м/с"
            ivWeather.setImageResource(
                WeatherMapper.codeToIcon(item.weatherCode, item.isDaytime)
            )
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<HourlyForecast>) {
        items = newItems
        notifyDataSetChanged()
    }
}