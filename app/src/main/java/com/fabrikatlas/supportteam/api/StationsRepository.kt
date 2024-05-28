package com.fabrikatlas.supportteam.api

import android.content.Context
import android.util.Log
import com.fabrikatlas.supportteam.dataclasses.Stations
import com.fabrikatlas.supportteam.dataclasses.StationsProvider

class StationsRepository {
    private val api = MyApiServices()

    suspend fun getAllStations():List<Stations>{
        Log.d("Creado", "En Get all comments")
        val response:List<Stations> = api.getAllStations()
        //Log.d("Creado", "All comments getted")
        StationsProvider.stations = response
        return response
    }
}