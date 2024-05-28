package com.fabrikatlas.supportteam.adapter


import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fabrikatlas.supportteam.R
import com.fabrikatlas.supportteam.databinding.ItemStationBinding
import com.fabrikatlas.supportteam.dataclasses.Stations
import com.fabrikatlas.supportteam.services.Timer


class StationsViewHolder (view: View): RecyclerView.ViewHolder(view){
    val binding = ItemStationBinding.bind(view)



    fun render(stationModel: Stations){



        val timer = Timer(100000000, 1000)
        val animation = AnimationUtils.loadAnimation(binding.card.context, R.anim.blink)

        binding.tvIdStation.text = "Machine ID: " + stationModel.id.toString()
        binding.tvNameStation.text = stationModel.st_name
        binding.tvLineStation.text = "Line: " + stationModel.st_line
        binding.tvUnHappyOEEStation.text = "Unhappy OEE: " + stationModel.st_unhappy_oee + "%"
        binding.tvHappyOEEStation.text = "Happy OEE: " + stationModel.st_happy_oee + "%"
        binding.tvCreateAtStation.text = "Alarm Status: " + stationModel.al_status
        binding.tvUserName.text = "User: " + stationModel.user_name

        timer.startTimer(binding.tvTimer)
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


        }

        val red = ContextCompat.getColor(binding.card.context, com.fabrikatlas.supportteam.R.color.red)
        val yellow = ContextCompat.getColor(binding.card.context, com.fabrikatlas.supportteam.R.color.yellow)
        val purple = ContextCompat.getColor(binding.card.context, com.fabrikatlas.supportteam.R.color.purple)
        val pink = ContextCompat.getColor(binding.card.context, com.fabrikatlas.supportteam.R.color.pink)
        val white = ContextCompat.getColor(binding.card.context, com.fabrikatlas.supportteam.R.color.white)
        val green = ContextCompat.getColor(binding.card.context, com.fabrikatlas.supportteam.R.color.green)

        when(stationModel.al_status){
            1 -> binding.card.setCardBackgroundColor(red);
            2 -> binding.card.setCardBackgroundColor(yellow)
            3 -> binding.card.setCardBackgroundColor(purple)
            4 -> binding.card.setCardBackgroundColor(pink)
            5 -> binding.card.setCardBackgroundColor(white)
            6 -> {
                binding.card.setCardBackgroundColor(green)
                binding.card.startAnimation(animation)
            }
            else -> binding.card.setCardBackgroundColor(white)
        }
    }
}