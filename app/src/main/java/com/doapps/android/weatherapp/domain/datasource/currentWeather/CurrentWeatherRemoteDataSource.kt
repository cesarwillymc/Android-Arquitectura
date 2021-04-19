package com.doapps.android.weatherapp.domain.datasource.currentWeather

import com.doapps.android.weatherapp.domain.WeatherAppAPI
import com.doapps.android.weatherapp.domain.model.CurrentWeatherResponse
import retrofit2.Response



class CurrentWeatherRemoteDataSource (private val api: WeatherAppAPI) {

    suspend fun getCurrentWeatherByGeoCords(lat: Double, lon: Double, units: String): Response<CurrentWeatherResponse> = api.getCurrentByGeoCords(lat, lon, units)
}
