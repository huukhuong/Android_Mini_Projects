package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.model.Weather
import com.squareup.picasso.Picasso

class WeatherAdapter(private var list: ArrayList<Weather>) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = list[position]
        holder.status.text = item.status
        holder.hour.text = item.time
        Picasso.get().load(item.icon).into(holder.icon)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var status: TextView = itemView.findViewById<TextView>(R.id.status)
        var icon: ImageView = itemView.findViewById<ImageView>(R.id.icon)
        var hour: TextView = itemView.findViewById<TextView>(R.id.hour)
    }

}