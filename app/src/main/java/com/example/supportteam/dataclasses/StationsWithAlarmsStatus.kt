package com.example.supportteam.dataclasses

data class StationsWithAlarmsStatus (
    val id: Int,
    val st_name:String,
    val st_line: String,
    val st_unhappy_oee:String,
    val st_happy_oee: String,
    val created_at:String,
    val updated_at:String,
    val alarm_status: String
)