package com.doapps.android.conexionmodule.db.room.converter

import androidx.room.TypeConverter
import com.doapps.android.conexionmodule.network.model.ListItem
import com.doapps.android.conexionmodule.network.model.WeatherItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object DataConverter {

    @TypeConverter
    @JvmStatic
    fun stringToList(data: String?): List<ListItem>? {
        if (data == null) {
            return emptyList()
        }

        val listType: Type = object : TypeToken< List<ListItem>?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    @JvmStatic
    fun listToString(objects: List<ListItem>): String {
        val gson = Gson()
        return gson.toJson(objects)
    }

    @TypeConverter
    @JvmStatic
    fun weatherStringToList(data: String?): List<WeatherItem>? {
        if (data == null) {
            return emptyList()
        }
        val listType: Type = object : TypeToken< List<WeatherItem>?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    @JvmStatic
    fun weatherListToString(objects: List<WeatherItem>): String {
        val gson = Gson()
        return gson.toJson(objects)
    }
}
