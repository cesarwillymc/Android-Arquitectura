package com.doapps.android.weatherapp.repo.currentweather

import androidx.lifecycle.LiveData
import com.doapps.android.conexionmodule.db.room.entity.CurrentWeatherEntity
import com.doapps.android.weatherapp.utils.domain.RateLimiter
import com.doapps.android.weatherapp.utils.domain.Resource

interface CurrentWeatherRepository {
    val currentWeatherRateLimit: RateLimiter<String>
    fun loadCurrentWeatherByGeoCords(
        lat: Double,
        lon: Double,
        fetchRequired: Boolean,
        units: String
    ): LiveData<Resource<CurrentWeatherEntity>>
}