package com.doapps.android.domain.repo.searchcities

import androidx.lifecycle.LiveData
import com.doapps.android.conexionmodule.db.room.entity.CitiesForSearchEntity
import com.doapps.android.domain.utils.RateLimiter
import com.doapps.android.domain.utils.status.Resource

interface SearchCitiesRepository {

    val rateLimiter: RateLimiter<String>

    fun loadCitiesByCityName(cityName: String?): LiveData<Resource<List<CitiesForSearchEntity>>>

}