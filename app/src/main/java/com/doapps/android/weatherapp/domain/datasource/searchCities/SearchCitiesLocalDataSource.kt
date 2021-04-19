package com.doapps.android.weatherapp.domain.datasource.searchCities

import androidx.lifecycle.LiveData
import com.doapps.android.weatherapp.db.dao.CitiesForSearchDao
import com.doapps.android.weatherapp.db.entity.CitiesForSearchEntity
import com.doapps.android.weatherapp.domain.model.SearchResponse


class SearchCitiesLocalDataSource (private val citiesForSearchDao: CitiesForSearchDao) {

    fun getCityByName(cityName: String?): LiveData<List<CitiesForSearchEntity>> = citiesForSearchDao.getCityByName(cityName)

    fun insertCities(response: SearchResponse) {
        response.hits
            ?.map { CitiesForSearchEntity(it) }
            ?.let { citiesForSearchDao.insertCities(it) }
    }
}
