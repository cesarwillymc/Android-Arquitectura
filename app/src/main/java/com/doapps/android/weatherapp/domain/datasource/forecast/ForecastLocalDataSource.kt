package com.doapps.android.weatherapp.domain.datasource.forecast

import com.doapps.android.weatherapp.db.dao.ForecastDao
import com.doapps.android.weatherapp.db.entity.ForecastEntity
import com.doapps.android.weatherapp.domain.model.ForecastResponse



class ForecastLocalDataSource (private val forecastDao: ForecastDao) {

    fun getForecast() = forecastDao.getForecast()

    fun insertForecast(forecast: ForecastResponse) = forecastDao.deleteAndInsert(ForecastEntity(forecast))
}
