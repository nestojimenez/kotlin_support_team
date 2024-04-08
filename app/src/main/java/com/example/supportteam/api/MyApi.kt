package com.example.supportteam.api

import com.example.supportteam.dataclasses.Stations
import com.example.supportteam.dataclasses.SupportAlarms
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyApi {
    @GET("support_alarm/{al_status}")
    fun getSupportAlarms(@Path("al_status") al_status:Int?): Call<List<SupportAlarms>>

    @GET("support_alarm_station/{al_status}")
    fun getSupportAlarmStation(@Path("al_status") al_status:Int?): Call<List<Stations>>

    @GET("stations/{id}")
    fun getStationsById(@Path("id") id:Int?): Call<List<Stations>>
}