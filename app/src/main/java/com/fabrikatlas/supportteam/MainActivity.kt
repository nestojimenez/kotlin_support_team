package com.fabrikatlas.supportteam

import android.Manifest
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabrikatlas.supportteam.adapter.StationsAdapter
import com.fabrikatlas.supportteam.databinding.ActivityMainBinding
import com.fabrikatlas.supportteam.dataclasses.Stations
import com.fabrikatlas.supportteam.dataclasses.StationsProvider
import com.fabrikatlas.supportteam.services.RunningServices
import com.fabrikatlas.supportteam.viewmodel.StationsViewModel
import com.google.gson.Gson

//Add play sound when alarm is activated - DONE
//Add name of person triggering the alarm o changing status - DONE
//Add to DB all operators on floor - DONE
//Add current alarm time - DONE
//Add DiffUtil to update recyclerview in order to have a specific timer
//for each alarm - DONE
//TODO turn off sound by clicking each card - DONE
//TODO add machine stations under alarm

class MainActivity : ComponentActivity() {


    private lateinit var binding: ActivityMainBinding

    val stationsViewModel: StationsViewModel by viewModels()

    private lateinit var mediaPlayer: MediaPlayer

    //private var timer = Timer(100000000, 5000)

    private lateinit var stationsAdapter : StationsAdapter
    private var stationsListUnMutable = listOf<Stations>()

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

        //timer.startTimer()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SocketHandler.setSocket()

        val mSocket = SocketHandler.getSocket()

        //binding.btnStart.setOnClickListener {
            Intent(applicationContext, RunningServices::class.java).also {
                it.action = RunningServices.Actions.START.toString()
                startService(it)
            }
        //}

        binding.btnStop.setOnClickListener {
            Intent(applicationContext, RunningServices::class.java).also {
                it.action = RunningServices.Actions.STOP.toString()
                startService(it)
            }
        }

        stationsViewModel.onCreate()

        val manager = LinearLayoutManager(this@MainActivity)
        stationsAdapter = StationsAdapter(StationsProvider.stations)
        binding.rvStations.layoutManager = manager
        binding.rvStations.adapter = stationsAdapter
        Log.i("StationsViewModel2", StationsProvider.stations.toString())
        stationsListUnMutable = StationsProvider.stations

        stationsViewModel.stationModel.observe(this) {
            Log.i("StationsViewModel", it.toString())
            Log.i("StationsViewModel2", StationsProvider.stations.toString())
            stationsListUnMutable = StationsProvider.stations
            stationsAdapter.updateList(StationsProvider.stations.filter { it.al_status != 5 })
            /*stationsAdapter = StationsAdapter(it.filter { it.al_status != 5 })
            binding.rvStations.layoutManager = manager
            binding.rvStations.adapter = stationsAdapter*/

            if(it.filter { it.al_status != 5 }.isEmpty()) {
                binding.tvTitle2.text = getText(R.string.title_no_alarms)
            }else{
                Log.i("AlarmsActivated", it.toString())
                binding.tvTitle2.text = getText(R.string.title_alarms_activated)
            }
        }


        mSocket.on("station_alarm_for_user") { args ->
            Log.i("Args", args[0].toString())
            if (args[0] != null) {
                //counter = args[0] as Int
                val stationAlarmed = args[0] as String


                Log.i("Alarmed_Start", StationsProvider.stations.toString())

                val gson = Gson()
                val stationAlarmedObj: Stations =
                    gson.fromJson(stationAlarmed, Stations::class.java)
                Log.i("Alarmed", stationAlarmedObj.al_status.toString())

                //ringTone(stationAlarmedObj.al_status)
                //Add stationAlarmedObj to stationModel from StationsViewModel
                //Modify Station model with new alarm status when alarm already exist on stationModel
                val currentStations = stationsViewModel.stationModel.value?.toMutableList()

                if (currentStations != null) {
                    currentStations.filter { it.al_status != 5}

                    if (currentStations.find{ it.id_stations == stationAlarmedObj.id_stations } != null) {
                        val index = currentStations.indexOf(currentStations.find{ it.id_stations == stationAlarmedObj.id_stations })
                        currentStations[index] = stationAlarmedObj
                        StationsProvider.stations = currentStations
                        stationsViewModel.stationModel.postValue(currentStations)
                        Log.i("StationsViewModel", currentStations.toString())
                    }else{
                        currentStations.add(stationAlarmedObj)
                        StationsProvider.stations = currentStations
                        stationsViewModel.stationModel.postValue(currentStations)

                        Log.i("StationsViewModel", currentStations.toString())
                    }
                }else{
                    stationsViewModel.stationModel.postValue(listOf(stationAlarmedObj))
                }

                //Make the phone vibrate
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

                //val sound = getSystemService(Context.AUDIO_SERVICE) as AudioManager
                //sound.playSoundEffect(AudioManager.FX_KEY_CLICK, 8f)



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

    }
}

