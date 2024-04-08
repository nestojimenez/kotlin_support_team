package com.example.supportteam.adapter


import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.supportteam.R
import com.example.supportteam.databinding.ItemStationBinding
import com.example.supportteam.dataclasses.Stations


class StationsViewHolder (view: View): RecyclerView.ViewHolder(view){
    val binding = ItemStationBinding.bind(view)

    fun render(stationModel: Stations){

        binding.tvIdStation.text = "Machine ID: " + stationModel.id.toString()
        binding.tvNameStation.text = stationModel.st_name
        binding.tvLineStation.text = "Production Line: " + stationModel.st_line
        binding.tvUnHappyOEEStation.text = "Unhappy OEE: " + stationModel.st_unhappy_oee + "%"
        binding.tvHappyOEEStation.text = "Happy OEE: " + stationModel.st_happy_oee + "%"
        binding.tvCreateAtStation.text = "Alarm Status: " + stationModel.al_status
        //binding.tvUpdatedAtStation.text = stationModel.updated_at.substring(0, 10)


        Glide.with(binding.ivStation.context).load("http://10.105.173.111:1880/ID" + stationModel.id).into(binding.ivStation)
//http://10.105.169.64:1880/ima1
        //https://picsum.photos/150/150
        itemView.setOnClickListener {
            Toast.makeText(
                binding.ivStation.context,
                stationModel.st_name,
                Toast.LENGTH_LONG
            ).show()
            //mSocket.emit("station_alarm", stationModel.st_name)
        }

        val red = ContextCompat.getColor(binding.card.context, com.example.supportteam.R.color.red)
        when(stationModel.al_status){
            1 -> binding.card.setCardBackgroundColor(red);
        }
    }
}