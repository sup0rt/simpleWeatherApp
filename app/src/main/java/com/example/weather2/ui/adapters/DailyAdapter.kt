package com.example.weather2.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather2.model.DailyForecast
import com.example.weather2.databinding.ItemDailyBinding
import com.example.weather2.utils.WeatherMapper

class DailyAdapter : RecyclerView.Adapter<DailyAdapter.ViewHolder>() {
    private var items = emptyList<DailyForecast>()

    inner class ViewHolder(val binding: ItemDailyBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDailyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            tvDate.text = item.date
            tvWeather.text = WeatherMapper.codeToDescription(item.weatherCode)
            tvTempRange.text = "${item.tempMin}° / ${item.tempMax}°"
            tvWind.text = "${item.windSpeed} м/с"
            ivWeather.setImageResource(
                WeatherMapper.codeToIcon(item.weatherCode, item.isDay)
            )
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<DailyForecast>) {
        items = newItems
        notifyDataSetChanged()
    }
}