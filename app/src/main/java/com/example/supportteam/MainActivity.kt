package com.example.supportteam

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.supportteam.adapter.StationsAdapter
import com.example.supportteam.api.MyApi
import com.example.supportteam.databinding.ActivityMainBinding
import com.example.supportteam.dataclasses.Stations
import com.example.supportteam.dataclasses.StationsWithAlarmsStatus
import com.example.supportteam.dataclasses.SupportAlarms
import com.example.supportteam.services.RunningServices
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : ComponentActivity() {


    private lateinit var binding: ActivityMainBinding

    private var stationsList = listOf<Stations>()

    private val BASE_URL = "http://10.105.168.234:8000"

    //http://10.105.169.83:3000
    private val TAG: String = "CHECK_RESPONSE"

    private var responseAPI = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Request Permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SocketHandler.setSocket()

        val mSocket = SocketHandler.getSocket()

        binding.btnStart.setOnClickListener {
            Intent(applicationContext, RunningServices::class.java).also {
                it.action = RunningServices.Actions.START.toString()
                startService(it)
            }
        }

        binding.btnStop.setOnClickListener {
            Intent(applicationContext, RunningServices::class.java).also {
                it.action = RunningServices.Actions.STOP.toString()
                startService(it)
            }
        }


        mSocket.on("station_alarm_for_user") { args ->
            Log.i("Args", args[0].toString())
            if (args[0] != null) {
                //counter = args[0] as Int
                val stationAlarmed = args[0] as String


                Log.i("Alarmed", stationAlarmed)

                val gson = Gson()
                val stationAlarmedObj: Stations =
                    gson.fromJson(stationAlarmed, Stations::class.java)
                Log.i("Alarmed", stationAlarmedObj.toString())

                runOnUiThread {
                    stationsList += stationAlarmedObj
                    val manager = LinearLayoutManager(this@MainActivity)
                    binding.rvStations.layoutManager = manager
                    binding.rvStations.adapter = StationsAdapter(stationsList)
                }

                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (vibrator.hasVibrator()) { // Vibrator availability checking
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(
                            VibrationEffect.createOneShot(
                                1000,
                                VibrationEffect.DEFAULT_AMPLITUDE
                            )
                        ) // New vibrate method for API Level 26 or higher
                    } else {
                        vibrator.vibrate(1000) // Vibrate method for below API Level 26
                    }
                }
            }
        }

        mSocket.connect()
        loadAlarms()
    }

    private fun loadAlarms() {
        var alarmsJson = ""

        val apiStations = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)

        apiStations.getSupportAlarmStation(1)
            .enqueue(object : Callback<List<Stations>> {
                override fun onResponse(
                    call: Call<List<Stations>>,
                    response: Response<List<Stations>>
                ) {
                    val activeAlarms = response.body()!!
                    Log.i("SupportAlarms_code", activeAlarms[0].id.toString())
                    //Make a dataclass that include al_status, make the sql query to ive back the al_status field when askin for stations with alarm
                    stationsList += activeAlarms

                    val manager = LinearLayoutManager(this@MainActivity)
                    binding.rvStations.layoutManager = manager
                    binding.rvStations.adapter = StationsAdapter(stationsList)
                }

                override fun onFailure(call: Call<List<Stations>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }
}

