package com.example.supportteam.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class SupportAlarms (
    val id: Int,
    val id_stations:Int,
    val al_status: Int,
    val created_at:String,
    val updated_at:String
)

//Status Code
//1 - Alarm just open
//2 - CTF on acknowledge on floor
//3 - Alarm closed