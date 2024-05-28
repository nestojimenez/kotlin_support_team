package com.fabrikatlas.supportteam.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.fabrikatlas.supportteam.core.RetrofitHelper
import com.fabrikatlas.supportteam.dataclasses.Stations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class MyApiServices {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getAllStations(): List<Stations> {
        return withContext(Dispatchers.IO) {

            try {
                val response: Response<List<Stations>> =
                    retrofit.create(MyApi::class.java).getAllStations()
                response.body() ?: emptyList()
            } catch (e: Exception) {
                Log.i("NetworkConnectionError", e.toString())

                emptyList()
            }


        }

    }

}