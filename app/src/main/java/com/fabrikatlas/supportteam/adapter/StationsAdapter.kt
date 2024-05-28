package com.fabrikatlas.supportteam.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fabrikatlas.supportteam.R
import com.fabrikatlas.supportteam.dataclasses.Stations

class StationsAdapter(private var stationsList:List<Stations>): RecyclerView.Adapter<StationsViewHolder>() {

    fun updateList(newList: List<Stations>){
        val stationsDiff = StationsDiffUtil(stationsList, newList)
        val result = DiffUtil.calculateDiff(stationsDiff)
        stationsList = newList
        result.dispatchUpdatesTo(this)
    }

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