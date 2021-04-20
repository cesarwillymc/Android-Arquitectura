package com.doapps.android.conexionmodule.db.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.doapps.android.conexionmodule.db.room.converter.DataConverter
import com.doapps.android.conexionmodule.db.room.dao.CitiesForSearchDao
import com.doapps.android.conexionmodule.db.room.dao.CurrentWeatherDao
import com.doapps.android.conexionmodule.db.room.dao.ForecastDao
import com.doapps.android.conexionmodule.db.room.entity.CitiesForSearchEntity
import com.doapps.android.conexionmodule.db.room.entity.CurrentWeatherEntity
import com.doapps.android.conexionmodule.db.room.entity.ForecastEntity

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
        private var INSTANCE: WeatherDatabase?=null
        private val LOCK= Any()
        operator fun invoke(context: Context)= INSTANCE ?: synchronized(LOCK){
            INSTANCE ?: buildDatabase(context)
        }
        private fun buildDatabase(context: Context)= Room.databaseBuilder(context,
            WeatherDatabase::class.java, "WeatherApp-DaB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}
