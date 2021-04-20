package com.doapps.android.weatherapp.ui.dashboard

import com.doapps.android.conexionmodule.network.model.ListItem
import com.doapps.android.weatherapp.utils.Mapper



class ForecastMapper : Mapper<List<ListItem>, List<ListItem>> {
    override fun mapFrom(type: List<ListItem>): List<ListItem> {
        val days = arrayListOf<String>()
        val mappedArray = arrayListOf<ListItem>()

        type.forEachIndexed { _, listItem ->
            if (days.contains(listItem.dtTxt?.substringBefore(" ")).not()) // Add day to days
                listItem.dtTxt?.substringBefore(" ")?.let { days.add(it) }
        }

        days.forEach { day ->

            // Find min and max temp values each of the day
            val array = type.filter { it.dtTxt?.substringBefore(" ").equals(day) }

            val minTemp = array.minByOrNull { it.main?.tempMin ?: 0.0 }?.main?.tempMin
            val maxTemp = array.maxByOrNull { it.main?.tempMax ?: 0.0 }?.main?.tempMax

            array.forEach {
                it.main?.tempMin = minTemp // Set min
                it.main?.tempMax = maxTemp // Set max
                mappedArray.add(it) // add it to mappedArray
            }
        }

        return mappedArray
            .filter { it.dtTxt?.substringAfter(" ")?.substringBefore(":")?.toInt()!! >= 12 }
            .distinctBy { it.getDay() } // Eliminate same days
            .toList() // Return as list
    }
}
