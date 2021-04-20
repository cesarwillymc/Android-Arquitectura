package com.doapps.android.domain.repo.currentweather

import NetworkBoundResource
import androidx.lifecycle.LiveData
import com.doapps.android.conexionmodule.network.conexion.config.SafeApiRequest
import com.doapps.android.conexionmodule.network.model.CurrentWeatherResponse
import com.doapps.android.conexionmodule.db.room.entity.CurrentWeatherEntity
import com.doapps.android.domain.datasource.currentWeather.CurrentWeatherLocalDataSource
import com.doapps.android.domain.datasource.currentWeather.CurrentWeatherRemoteDataSource
import com.doapps.android.domain.utils.Constants.NetworkService.RATE_LIMITER_TYPE
import com.doapps.android.domain.utils.RateLimiter
import com.doapps.android.domain.utils.status.Resource

import java.util.concurrent.TimeUnit


class CurrentWeatherRepositoryImpl(
    private val currentWeatherRemoteDataSource: CurrentWeatherRemoteDataSource,
    private val currentWeatherLocalDataSource: CurrentWeatherLocalDataSource
) : SafeApiRequest(), CurrentWeatherRepository {

    override val currentWeatherRateLimit = RateLimiter<String>(30, TimeUnit.SECONDS)

    override fun loadCurrentWeatherByGeoCords(
        lat: Double,
        lon: Double,
        fetchRequired: Boolean,
        units: String
    ): LiveData<Resource<CurrentWeatherEntity>> {
        return object : NetworkBoundResource<CurrentWeatherEntity, CurrentWeatherResponse>() {
            override suspend fun saveCallResult(item: CurrentWeatherResponse) =
                currentWeatherLocalDataSource.insertCurrentWeather(item)

            override fun shouldFetch(data: CurrentWeatherEntity?): Boolean = fetchRequired

            override fun loadFromDb(): LiveData<CurrentWeatherEntity> =
                currentWeatherLocalDataSource.getCurrentWeather()

            override suspend fun createCall(): CurrentWeatherResponse = apiRequest {
                currentWeatherRemoteDataSource.getCurrentWeatherByGeoCords(
                    lat,
                    lon,
                    units
                )
            }

            override fun onFetchFailed() = currentWeatherRateLimit.reset(RATE_LIMITER_TYPE)
        }.asLiveData
    }
}
