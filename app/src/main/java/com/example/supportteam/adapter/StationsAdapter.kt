package com.example.supportteam.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supportteam.R
import com.example.supportteam.dataclasses.Stations

class StationsAdapter(private val stationsList:List<Stations>): RecyclerView.Adapter<StationsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return StationsViewHolder(layoutInflater.inflate(R.layout.item_station, parent, false))
    }

    override fun getItemCount(): Int {
        Log.i("stationListSize", stationsList.size.toString())
        return stationsList.size
    }

    override fun onBindViewHolder(holder: StationsViewHolder, position: Int) {
        val item = stationsList[position]
        holder.render(item)
    }

}