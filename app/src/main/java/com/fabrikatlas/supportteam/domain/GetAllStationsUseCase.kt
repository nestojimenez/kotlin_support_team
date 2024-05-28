package com.fabrikatlas.supportteam.domain

import android.content.Context
import com.fabrikatlas.supportteam.api.StationsRepository
import com.fabrikatlas.supportteam.dataclasses.Stations

class GetAllStationsUseCase {
    private val repository = StationsRepository()

    suspend operator fun invoke():List<Stations>? = repository.getAllStations()
}