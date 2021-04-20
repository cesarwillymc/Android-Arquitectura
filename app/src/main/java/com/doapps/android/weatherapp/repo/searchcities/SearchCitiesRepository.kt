package com.doapps.android.weatherapp.repo.searchcities

import androidx.lifecycle.LiveData
import com.doapps.android.conexionmodule.db.room.entity.CitiesForSearchEntity
import com.doapps.android.weatherapp.utils.domain.RateLimiter
import com.doapps.android.weatherapp.utils.domain.Resource

interface SearchCitiesRepository {

    val rateLimiter: RateLimiter<String>

    fun loadCitiesByCityName(cityName: String?): LiveData<Resource<List<CitiesForSearchEntity>>>

}