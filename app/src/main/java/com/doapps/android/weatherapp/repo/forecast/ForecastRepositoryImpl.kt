package com.doapps.android.weatherapp.repo.forecast

import NetworkBoundResource
import androidx.lifecycle.LiveData
import com.doapps.android.conexionmodule.network.conexion.config.SafeApiRequest
import com.doapps.android.conexionmodule.network.model.ForecastResponse
import com.doapps.android.weatherapp.core.Constants.NetworkService.RATE_LIMITER_TYPE
import com.doapps.android.conexionmodule.db.room.entity.ForecastEntity
import com.doapps.android.weatherapp.domain.datasource.forecast.ForecastLocalDataSource
import com.doapps.android.weatherapp.domain.datasource.forecast.ForecastRemoteDataSource
import com.doapps.android.weatherapp.utils.domain.RateLimiter
import com.doapps.android.weatherapp.utils.domain.Resource
import java.util.concurrent.TimeUnit


class ForecastRepositoryImpl(
    private val forecastRemoteDataSource: ForecastRemoteDataSource,
    private val forecastLocalDataSource: ForecastLocalDataSource
) : SafeApiRequest(), ForecastRepository {

    override val forecastListRateLimit = RateLimiter<String>(30, TimeUnit.SECONDS)

    override fun loadForecastByCoord(
        lat: Double,
        lon: Double,
        fetchRequired: Boolean,
        units: String
    ): LiveData<Resource<ForecastEntity>> {
        return object : NetworkBoundResource<ForecastEntity, ForecastResponse>() {
            override suspend fun saveCallResult(item: ForecastResponse) =
                forecastLocalDataSource.insertForecast(item)

            override fun shouldFetch(data: ForecastEntity?): Boolean = fetchRequired

            override fun loadFromDb(): LiveData<ForecastEntity> =
                forecastLocalDataSource.getForecast()

            override suspend fun createCall(): ForecastResponse =
                apiRequest { forecastRemoteDataSource.getForecastByGeoCords(lat, lon, units) }

            override fun onFetchFailed() = forecastListRateLimit.reset(RATE_LIMITER_TYPE)
        }.asLiveData
    }
}
