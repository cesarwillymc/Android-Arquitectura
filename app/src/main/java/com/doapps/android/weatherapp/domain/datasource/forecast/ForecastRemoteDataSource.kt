package com.doapps.android.weatherapp.domain.datasource.forecast

import com.doapps.android.weatherapp.domain.WeatherAppAPI
import com.doapps.android.weatherapp.domain.model.ForecastResponse
import retrofit2.Response



class ForecastRemoteDataSource (private val api: WeatherAppAPI) {

    suspend fun getForecastByGeoCords(lat: Double, lon: Double, units: String): Response<ForecastResponse> =  api.getForecastByGeoCords(lat, lon, units)
}
