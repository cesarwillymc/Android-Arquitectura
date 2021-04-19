package com.doapps.android.weatherapp.domain

import android.os.Environment
import com.doapps.android.weatherapp.BuildConfig
import com.doapps.android.weatherapp.core.Constants
import com.doapps.android.weatherapp.domain.model.CurrentWeatherResponse
import com.doapps.android.weatherapp.domain.model.ForecastResponse
import com.google.android.gms.common.ConnectionResult.TIMEOUT
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

import java.util.concurrent.TimeUnit

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
        var TIMEOUT = 10
        operator fun invoke(): WeatherAppAPI {
            val cache = Cache(Environment.getDownloadCacheDirectory(), 10 * 1024 * 1024)
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
                .addInterceptor(DefaultRequestInterceptor())
                .cache(cache)
                .build()


            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherAppAPI::class.java)
        }
    }
}
