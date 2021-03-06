package com.doapps.android.weatherapp.repo

import NetworkBoundResource
import androidx.lifecycle.LiveData
import com.doapps.android.weatherapp.core.Constants.NetworkService.RATE_LIMITER_TYPE
import com.doapps.android.weatherapp.db.entity.CitiesForSearchEntity
import com.doapps.android.weatherapp.domain.datasource.searchCities.SearchCitiesLocalDataSource
import com.doapps.android.weatherapp.domain.datasource.searchCities.SearchCitiesRemoteDataSource
import com.doapps.android.weatherapp.domain.model.SearchResponse
import com.doapps.android.weatherapp.utils.domain.RateLimiter
import com.doapps.android.weatherapp.utils.domain.Resource

import java.util.concurrent.TimeUnit



class SearchCitiesRepository (
    private val searchCitiesLocalDataSource: SearchCitiesLocalDataSource,
    private val searchCitiesRemoteDataSource: SearchCitiesRemoteDataSource
){

    private val rateLimiter = RateLimiter<String>(1, TimeUnit.SECONDS)

    fun loadCitiesByCityName(cityName: String?): LiveData<Resource<List<CitiesForSearchEntity>>> {
        return object : NetworkBoundResource<List<CitiesForSearchEntity>, SearchResponse>() {
            override suspend fun saveCallResult(item: SearchResponse) = searchCitiesLocalDataSource.insertCities(item)

            override fun shouldFetch(data: List<CitiesForSearchEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<CitiesForSearchEntity>> = searchCitiesLocalDataSource.getCityByName(cityName)

            override suspend fun createCall(): SearchResponse = searchCitiesRemoteDataSource.getCityWithQuery(
                cityName
                    ?: ""
            )

            override fun onFetchFailed() = rateLimiter.reset(RATE_LIMITER_TYPE)
        }.asLiveData
    }
}
