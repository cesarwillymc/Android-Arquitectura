package com.doapps.android.weatherapp.repo

import NetworkBoundResource
import androidx.lifecycle.LiveData
import com.doapps.android.weatherapp.core.Constants.NetworkService.RATE_LIMITER_TYPE
import com.doapps.android.weatherapp.db.entity.ForecastEntity
import com.doapps.android.weatherapp.domain.SafeApiRequest
import com.doapps.android.weatherapp.domain.datasource.forecast.ForecastLocalDataSource
import com.doapps.android.weatherapp.domain.datasource.forecast.ForecastRemoteDataSource
import com.doapps.android.weatherapp.domain.model.ForecastResponse
import com.doapps.android.weatherapp.utils.domain.RateLimiter
import com.doapps.android.weatherapp.utils.domain.Resource
import java.util.concurrent.TimeUnit



class ForecastRepository (
    private val forecastRemoteDataSource: ForecastRemoteDataSource,
    private val forecastLocalDataSource: ForecastLocalDataSource
):SafeApiRequest() {

    private val forecastListRateLimit = RateLimiter<String>(30, TimeUnit.SECONDS)

    fun loadForecastByCoord(lat: Double, lon: Double, fetchRequired: Boolean, units: String): LiveData<Resource<ForecastEntity>> {
        return object : NetworkBoundResource<ForecastEntity, ForecastResponse>() {
            override suspend fun saveCallResult(item: ForecastResponse) = forecastLocalDataSource.insertForecast(item)

            override fun shouldFetch(data: ForecastEntity?): Boolean = fetchRequired

            override fun loadFromDb(): LiveData<ForecastEntity> = forecastLocalDataSource.getForecast()

            override suspend fun createCall(): ForecastResponse = apiRequest { forecastRemoteDataSource.getForecastByGeoCords(lat, lon, units) }

            override fun onFetchFailed() = forecastListRateLimit.reset(RATE_LIMITER_TYPE)
        }.asLiveData
    }
}
