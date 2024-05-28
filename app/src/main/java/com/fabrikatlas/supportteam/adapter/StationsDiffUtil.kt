package com.fabrikatlas.supportteam.adapter

import androidx.recyclerview.widget.DiffUtil
import com.fabrikatlas.supportteam.dataclasses.Stations

class StationsDiffUtil(
    private val oldList: List<Stations>,
    private val newList: List<Stations>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(p0: Int, p1: Int): Boolean {
        return oldList[p0].AlarmId == newList[p1].AlarmId
    }

    override fun areContentsTheSame(p0: Int, p1: Int): Boolean {
        return oldList[p0] == newList[p1]
    }

}