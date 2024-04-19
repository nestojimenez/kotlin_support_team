package com.example.supportteam.domain

import com.example.supportteam.api.StationsRepository
import com.example.supportteam.dataclasses.Stations

class GetAllStationsUseCase {
    private val repository = StationsRepository()

    suspend operator fun invoke():List<Stations>? = repository.getAllStations()
}