package com.doapps.android.weatherapp.domain.datasource.currentWeather

import com.doapps.android.weatherapp.db.dao.CurrentWeatherDao
import com.doapps.android.weatherapp.db.entity.CurrentWeatherEntity
import com.doapps.android.weatherapp.domain.model.CurrentWeatherResponse




class CurrentWeatherLocalDataSource (private val currentWeatherDao: CurrentWeatherDao) {

    fun getCurrentWeather() = currentWeatherDao.getCurrentWeather()

    suspend fun insertCurrentWeather(currentWeather: CurrentWeatherResponse) = currentWeatherDao.deleteAndInsert(CurrentWeatherEntity(currentWeather))
}
