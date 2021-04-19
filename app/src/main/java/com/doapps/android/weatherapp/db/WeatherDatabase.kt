package com.doapps.android.weatherapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.doapps.android.weatherapp.db.dao.CitiesForSearchDao
import com.doapps.android.weatherapp.db.dao.CurrentWeatherDao
import com.doapps.android.weatherapp.db.dao.ForecastDao
import com.doapps.android.weatherapp.db.entity.CitiesForSearchEntity
import com.doapps.android.weatherapp.db.entity.CurrentWeatherEntity
import com.doapps.android.weatherapp.db.entity.ForecastEntity
import com.doapps.android.weatherapp.utils.typeconverters.DataConverter

@Database(
    entities = [
        ForecastEntity::class,
        CurrentWeatherEntity::class,
        CitiesForSearchEntity::class
    ],
    version = 2
)
@TypeConverters(DataConverter::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastDao

    abstract fun currentWeatherDao(): CurrentWeatherDao

    abstract fun citiesForSearchDao(): CitiesForSearchDao
    companion object{
        @Volatile
        private var INSTANCE:WeatherDatabase?=null
        private val LOCK= Any()
        operator fun invoke(context: Context)= INSTANCE?: synchronized(LOCK){
            INSTANCE?:buildDatabase(context)
        }
        private fun buildDatabase(context: Context)= Room.databaseBuilder(context,WeatherDatabase::class.java, "WeatherApp-DaB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}
