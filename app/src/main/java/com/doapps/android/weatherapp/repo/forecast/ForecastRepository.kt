package com.doapps.android.weatherapp.repo.forecast

import androidx.lifecycle.LiveData
import com.doapps.android.conexionmodule.db.room.entity.ForecastEntity
import com.doapps.android.weatherapp.utils.domain.RateLimiter
import com.doapps.android.weatherapp.utils.domain.Resource

interface ForecastRepository {

    val forecastListRateLimit: RateLimiter<String>
    fun loadForecastByCoord(
        lat: Double,
        lon: Double,
        fetchRequired: Boolean,
        units: String
    ): LiveData<Resource<ForecastEntity>>
}