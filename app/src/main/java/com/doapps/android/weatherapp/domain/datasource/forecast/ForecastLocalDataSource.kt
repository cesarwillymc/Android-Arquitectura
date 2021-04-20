package com.doapps.android.weatherapp.domain.datasource.forecast

import com.doapps.android.conexionmodule.network.model.ForecastResponse
import com.doapps.android.conexionmodule.db.room.dao.ForecastDao
import com.doapps.android.conexionmodule.db.room.entity.ForecastEntity



class ForecastLocalDataSource (private val forecastDao: ForecastDao) {

    fun getForecast() = forecastDao.getForecast()

    fun insertForecast(forecast: ForecastResponse) = forecastDao.deleteAndInsert(ForecastEntity(forecast))
}
