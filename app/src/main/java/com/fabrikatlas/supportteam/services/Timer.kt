package com.fabrikatlas.supportteam.services
import android.os.CountDownTimer
import android.util.Log
import android.widget.TextView

class Timer (private var millisInFuture: Long, private val countDownInterval: Long){



        private var timer: CountDownTimer? = null

        fun startTimer(textView: TextView) {
            timer = object : CountDownTimer(millisInFuture, countDownInterval) {
                override fun onTick(millisUntilFinished: Long) {
                    millisInFuture = millisUntilFinished
                    // You can update UI here
                    val seconds = (100000000 - millisUntilFinished) / 1000 %60
                    val minutes = (100000000 - millisUntilFinished) / 1000 / 60 %60
                    val hours = (100000000 - millisUntilFinished) / 1000 / 60 / 60 %60
                    Log.i("Timer", "Seconds remaining: " + seconds)
                    Log.i("Timer", "Minutes remaining: " + minutes)
                    Log.i("Timer", "Hours remaining: " + hours)

                    val secondsString = if (seconds < 10) "0$seconds" else "$seconds"
                    val minutesString = if (minutes < 10) "0$minutes" else "$minutes"
                    val hoursString = if (hours < 10) "0$hours" else "$hours"

                    textView.text = "Duration: $hoursString:$minutesString:$secondsString"
                    //Log.i("Timer", "Seconds Elapsed: " + (100000000 - millisUntilFinished)/1000)
                }

                override fun onFinish() {
                    // Timer finished
                }
            }.start()
        }

        fun stopTimer() {
            timer?.cancel()
        }

        fun resetTimer(initialMillis: Long) {
            stopTimer()
            millisInFuture = initialMillis
        }
    }
