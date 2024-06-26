package com.fabrikatlas.supportteam.services
import android.os.CountDownTimer
import android.util.Log
import android.widget.TextView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar

class Timer (private var millisInFuture: Long, private val countDownInterval: Long){



        private var timer: CountDownTimer? = null

        fun startTimer(textView: TextView, alarmStart: String) {
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

                    //val secondsString = if (seconds < 10) "0$seconds" else "$seconds"
                    //val minutesString = if (minutes < 10) "0$minutes" else "$minutes"
                    //val hoursString = if (hours < 10) "0$hours" else "$hours"

                    //Log.i("Alarm StartedAt", alarmStart)
                    //Log.i("Alarm StartedAt", Calendar.getInstance().time.toString())

                   //Get alarmStart Date and calculate alarm elapsed time
                    Log.i("Alarm StartedAt X", alarmStart)

                    if(alarmStart != "") {


                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")

                        val dateTime = LocalDateTime.now()
                        val currentTime: OffsetDateTime = LocalDateTime.parse(dateTime.toString())
                            // and append the desired offset
                            .atOffset(ZoneOffset.UTC)

                        val alarmStarted: OffsetDateTime =
                            LocalDateTime.parse(alarmStart, formatter)
                                // and append the desired offset
                                .atOffset(ZoneOffset.UTC)

                        val timeElapsed = currentTime.toEpochSecond() - alarmStarted.toEpochSecond()

                        val hoursElapsed = timeElapsed / 3600;
                        val minutesElapsed = (timeElapsed % 3600) / 60;
                        val secondsElapsed = timeElapsed % 60;

                        val secondsString =
                            if (secondsElapsed < 10) "0$secondsElapsed" else "$secondsElapsed"
                        val minutesString =
                            if (minutesElapsed < 10) "0$minutesElapsed" else "$minutesElapsed"
                        val hoursString =
                            if (hoursElapsed < 10) "0$hoursElapsed" else "$hoursElapsed"

                        Log.i("Alarm StartedAt time elapsed", timeElapsed.toString())
                        Log.i(
                            "Alarm StartedAt current time",
                            currentTime.format(formatter).toString()
                        )
                        Log.i(
                            "Alarm StartedAt alarm time",
                            alarmStarted.format(formatter).toString()
                        )

                        textView.text = "Duration: $hoursString:$minutesString:$secondsString"
                    }else{
                        textView.text = "Duration: No time available"
                    }
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
