package com.doapps.android.domain.datasource.forecast

import com.doapps.android.conexionmodule.network.conexion.WeatherAppAPI
import com.doapps.android.conexionmodule.network.model.ForecastResponse
import retrofit2.Response



class ForecastRemoteDataSource (private val api: WeatherAppAPI) {

    suspend fun getForecastByGeoCords(lat: Double, lon: Double, units: String): Response<ForecastResponse> =  api.getForecastByGeoCords(lat, lon, units)
}
