package com.doapps.android.domain.repo.currentweather

import androidx.lifecycle.LiveData
import com.doapps.android.conexionmodule.db.room.entity.CurrentWeatherEntity
import com.doapps.android.domain.utils.RateLimiter
import com.doapps.android.domain.utils.status.Resource

interface CurrentWeatherRepository {
    val currentWeatherRateLimit: RateLimiter<String>
    fun loadCurrentWeatherByGeoCords(
        lat: Double,
        lon: Double,
        fetchRequired: Boolean,
        units: String
    ): LiveData<Resource<CurrentWeatherEntity>>
}