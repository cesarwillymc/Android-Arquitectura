package com.doapps.android.domain.datasource.currentWeather

import com.doapps.android.conexionmodule.network.conexion.WeatherAppAPI
import com.doapps.android.conexionmodule.network.model.CurrentWeatherResponse
import retrofit2.Response



class CurrentWeatherRemoteDataSource (private val api: WeatherAppAPI) {

    suspend fun getCurrentWeatherByGeoCords(lat: Double, lon: Double, units: String): Response<CurrentWeatherResponse> = api.getCurrentByGeoCords(lat, lon, units)
}
