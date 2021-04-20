package com.doapps.android.domain.repo.forecast

import androidx.lifecycle.LiveData
import com.doapps.android.conexionmodule.db.room.entity.ForecastEntity
import com.doapps.android.domain.utils.RateLimiter
import com.doapps.android.domain.utils.status.Resource

interface ForecastRepository {

    val forecastListRateLimit: RateLimiter<String>
    fun loadForecastByCoord(
        lat: Double,
        lon: Double,
        fetchRequired: Boolean,
        units: String
    ): LiveData<Resource<ForecastEntity>>
}