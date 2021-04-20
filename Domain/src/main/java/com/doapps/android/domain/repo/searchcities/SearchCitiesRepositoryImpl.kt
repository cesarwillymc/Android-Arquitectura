package com.doapps.android.domain.repo.searchcities

import NetworkBoundResource
import androidx.lifecycle.LiveData
import com.doapps.android.conexionmodule.network.model.SearchResponse
import com.doapps.android.conexionmodule.db.room.entity.CitiesForSearchEntity
import com.doapps.android.domain.datasource.searchCities.SearchCitiesLocalDataSource
import com.doapps.android.domain.datasource.searchCities.SearchCitiesRemoteDataSource
import com.doapps.android.domain.utils.Constants.NetworkService.RATE_LIMITER_TYPE
import com.doapps.android.domain.utils.RateLimiter
import com.doapps.android.domain.utils.status.Resource

import java.util.concurrent.TimeUnit



class SearchCitiesRepositoryImpl (
    private val searchCitiesLocalDataSource: SearchCitiesLocalDataSource,
    private val searchCitiesRemoteDataSource: SearchCitiesRemoteDataSource
): SearchCitiesRepository {

    override val rateLimiter = RateLimiter<String>(1, TimeUnit.SECONDS)

    override fun loadCitiesByCityName(cityName: String?): LiveData<Resource<List<CitiesForSearchEntity>>> {
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
