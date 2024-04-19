package com.example.supportteam.api

import com.example.supportteam.core.RetrofitHelper
import com.example.supportteam.dataclasses.Stations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class MyApiServices {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getAllStations():List<Stations>{
        return withContext(Dispatchers.IO){
            val response: Response<List<Stations>> = retrofit.create(MyApi::class.java).getAllStations()
            response.body() ?: emptyList()
        }

    }

}