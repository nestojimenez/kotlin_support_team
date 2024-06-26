package com.fabrikatlas.supportteam.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabrikatlas.supportteam.dataclasses.Stations
import com.fabrikatlas.supportteam.domain.GetAllStationsUseCase
import kotlinx.coroutines.launch

class StationsViewModel : ViewModel() {

    val stationModel = MutableLiveData<List<Stations>>()

    var getAllStationsUseCase = GetAllStationsUseCase()

    fun onCreate(){
        viewModelScope.launch {
            Log.d("Creado", "En rutina de onCreate")
            val result = getAllStationsUseCase()
            Log.d("Creado", result.toString())
            if(!result.isNullOrEmpty()){
                stationModel.postValue(result)
                Log.d("Creado", "En rutina de postvalue")
            }
        }
    }
}