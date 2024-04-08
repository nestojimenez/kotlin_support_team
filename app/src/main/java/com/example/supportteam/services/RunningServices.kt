package com.example.supportteam.services

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.supportteam.R
import com.example.supportteam.SocketHandler
import com.example.supportteam.dataclasses.Stations
import com.google.gson.Gson

class RunningServices : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }


    private val mSocket = SocketHandler.getSocket()

    private fun start() {

        mSocket.on("station_alarm_for_user") { args ->
            val stationAlarmed = args[0] as String

            if (args[0] != null) {

                val gson = Gson()
                val stationAlarmedObj: Stations = gson.fromJson(stationAlarmed, Stations::class.java)

                val notification = NotificationCompat
                    .Builder(this, "running_channel")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Support Alarm")
                    .setContentText(stationAlarmedObj.st_name)
                    .build()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    startForeground(
                        1,
                        notification,
                        ServiceInfo.FOREGROUND_SERVICE_TYPE_REMOTE_MESSAGING
                    )
                }
            }
        }


    }

    enum class Actions {
        START, STOP
    }
}