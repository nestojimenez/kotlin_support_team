package com.fabrikatlas.supportteam

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketHandler {
    lateinit var mSocket: Socket

    @Synchronized
    fun setSocket (){
        try {
            mSocket = IO.socket("http://10.105.173.111:8080")
            Log.i("ConnectedSocketIO", "Connected")
        }catch (e: URISyntaxException){
            Log.i("ConnectedSocketIO", e.message.toString())
        }
    }

    @Synchronized
    fun getSocket() : Socket {
        return mSocket
    }
}