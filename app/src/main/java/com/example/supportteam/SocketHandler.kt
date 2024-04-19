package com.example.supportteam

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketHandler {
    lateinit var mSocket: Socket

    @Synchronized
    fun setSocket (){
        try {
            mSocket = IO.socket("http://10.105.168.231:3000")
            Log.i("ConnectedSocketIO", "Connected")
        }catch (e: URISyntaxException){

        }
    }

    @Synchronized
    fun getSocket() : Socket {
        return mSocket
    }
}