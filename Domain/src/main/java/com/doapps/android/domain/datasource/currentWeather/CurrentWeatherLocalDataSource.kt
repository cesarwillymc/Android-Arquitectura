package com.doapps.android.domain.datasource.currentWeather

import com.doapps.android.conexionmodule.network.model.CurrentWeatherResponse
import com.doapps.android.conexionmodule.db.room.dao.CurrentWeatherDao
import com.doapps.android.conexionmodule.db.room.entity.CurrentWeatherEntity




class CurrentWeatherLocalDataSource (private val currentWeatherDao: CurrentWeatherDao) {

    fun getCurrentWeather() = currentWeatherDao.getCurrentWeather()

    suspend fun insertCurrentWeather(currentWeather: CurrentWeatherResponse) = currentWeatherDao.deleteAndInsert(
        CurrentWeatherEntity(currentWeather)
    )
}
