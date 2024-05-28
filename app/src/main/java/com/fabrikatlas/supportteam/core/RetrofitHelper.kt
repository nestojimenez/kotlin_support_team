package com.fabrikatlas.supportteam.core

import android.util.Log
import android.widget.Toast
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private val BASE_URL = "http://10.105.173.111:8000"

    fun getRetrofit(): Retrofit {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        Log.i("RetrofitHelper", retrofit.toString())

        return retrofit


    }
}