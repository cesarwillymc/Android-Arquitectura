package com.doapps.android.weatherapp.repo

import NetworkBoundResource
import androidx.lifecycle.LiveData
import com.doapps.android.weatherapp.core.Constants.NetworkService.RATE_LIMITER_TYPE
import com.doapps.android.weatherapp.db.entity.CurrentWeatherEntity
import com.doapps.android.weatherapp.domain.SafeApiRequest
import com.doapps.android.weatherapp.domain.datasource.currentWeather.CurrentWeatherLocalDataSource
import com.doapps.android.weatherapp.domain.datasource.currentWeather.CurrentWeatherRemoteDataSource
import com.doapps.android.weatherapp.domain.model.CurrentWeatherResponse
import com.doapps.android.weatherapp.utils.domain.RateLimiter
import com.doapps.android.weatherapp.utils.domain.Resource

import java.util.concurrent.TimeUnit



class CurrentWeatherRepository (
    private val currentWeatherRemoteDataSource: CurrentWeatherRemoteDataSource,
    private val currentWeatherLocalDataSource: CurrentWeatherLocalDataSource
):SafeApiRequest() {

    private val currentWeatherRateLimit = RateLimiter<String>(30, TimeUnit.SECONDS)

    fun loadCurrentWeatherByGeoCords(lat: Double, lon: Double, fetchRequired: Boolean, units: String): LiveData<Resource<CurrentWeatherEntity>> {
        return object : NetworkBoundResource<CurrentWeatherEntity, CurrentWeatherResponse>() {
            override suspend fun saveCallResult(item: CurrentWeatherResponse) = currentWeatherLocalDataSource.insertCurrentWeather(item)

            override fun shouldFetch(data: CurrentWeatherEntity?): Boolean = fetchRequired

            override fun loadFromDb(): LiveData<CurrentWeatherEntity> = currentWeatherLocalDataSource.getCurrentWeather()

            override suspend fun createCall(): CurrentWeatherResponse = apiRequest { currentWeatherRemoteDataSource.getCurrentWeatherByGeoCords(lat, lon, units) }

            override fun onFetchFailed() = currentWeatherRateLimit.reset(RATE_LIMITER_TYPE)
        }.asLiveData
    }
}
