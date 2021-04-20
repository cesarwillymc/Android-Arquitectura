package com.doapps.android.conexionmodule.network.conexion


import com.doapps.android.conexionmodule.BuildConfig
import com.doapps.android.conexionmodule.network.conexion.config.HttpConfiguration
import com.doapps.android.conexionmodule.network.model.CurrentWeatherResponse
import com.doapps.android.conexionmodule.network.model.ForecastResponse

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAppAPI {

    @GET("forecast")
    suspend fun getForecastByGeoCords(
        @Query("lat")
        lat: Double,
        @Query("lon")
        lon: Double,
        @Query("units")
        units: String
    ): Response<ForecastResponse>

    @GET("weather")
    suspend fun getCurrentByGeoCords(
        @Query("lat")
        lat: Double,
        @Query("lon")
        lon: Double,
        @Query("units")
        units: String
    ): Response<CurrentWeatherResponse>

    companion object {
        operator fun invoke(httpConfiguration: HttpConfiguration): WeatherAppAPI {
            val okHttpClient= httpConfiguration.onCreate()
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherAppAPI::class.java)
        }
    }
}
