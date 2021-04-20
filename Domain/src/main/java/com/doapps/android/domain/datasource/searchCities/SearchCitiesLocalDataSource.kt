package com.doapps.android.domain.datasource.searchCities

import androidx.lifecycle.LiveData
import com.doapps.android.conexionmodule.network.model.SearchResponse
import com.doapps.android.conexionmodule.db.room.dao.CitiesForSearchDao
import com.doapps.android.conexionmodule.db.room.entity.CitiesForSearchEntity


class SearchCitiesLocalDataSource (private val citiesForSearchDao: CitiesForSearchDao) {

    fun getCityByName(cityName: String?): LiveData<List<CitiesForSearchEntity>> = citiesForSearchDao.getCityByName(cityName)

    fun insertCities(response: SearchResponse) {
        response.hits
            ?.map { CitiesForSearchEntity(it) }
            ?.let { citiesForSearchDao.insertCities(it) }
    }
}
