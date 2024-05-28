package com.fabrikatlas.supportteam.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.fabrikatlas.supportteam.R
import com.fabrikatlas.supportteam.SocketHandler
import com.fabrikatlas.supportteam.dataclasses.Stations
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

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun start() {

        /*val notification = NotificationCompat
            .Builder(this, "running_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Support Alarm")
            .setContentText("Support Alarm")
            .build()*/

        //startForeground(1, notification)

        mSocket.on("station_alarm_for_user") { args ->
            val stationAlarmed = args[0] as String

            if (args[0] != null) {

                //Make the phone vibrate
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (vibrator.hasVibrator()) { // Vibrator availability checking
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(
                            VibrationEffect.createOneShot(
                                5000,
                                VibrationEffect.EFFECT_HEAVY_CLICK
                            )
                        ) // New vibrate method for API Level 26 or higher
                    } else {
                        vibrator.vibrate(5000) // Vibrate method for below API Level 26
                    }
                }

                val gson = Gson()
                val stationAlarmedObj: Stations =
                    gson.fromJson(stationAlarmed, Stations::class.java)

                val notification = NotificationCompat
                    .Builder(this, "running_channel")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Support Alarm")
                    .setContentText(stationAlarmedObj.st_name)
                    .build()



                //In order to startForeground on Android 10 and above, we need to use the new method
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    startForeground(
                        1,
                        notification,
                        ServiceInfo.FOREGROUND_SERVICE_TYPE_REMOTE_MESSAGING
                    )
                } else {
                    startForeground(1, notification)
                }


            }
        }


    }

    enum class Actions {
        START, STOP
    }
}