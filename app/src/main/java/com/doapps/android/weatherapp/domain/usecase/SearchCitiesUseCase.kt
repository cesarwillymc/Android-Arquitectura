package com.doapps.android.weatherapp.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.doapps.android.weatherapp.db.entity.CitiesForSearchEntity
import com.doapps.android.weatherapp.repo.SearchCitiesRepository
import com.doapps.android.weatherapp.utils.UseCaseLiveData
import com.doapps.android.weatherapp.utils.domain.Resource



class SearchCitiesUseCase (private val repository: SearchCitiesRepository) : UseCaseLiveData<Resource<List<CitiesForSearchEntity>>, SearchCitiesUseCase.SearchCitiesParams, SearchCitiesRepository>() {

    override fun buildUseCaseObservable(params: SearchCitiesParams?): LiveData<Resource<List<CitiesForSearchEntity>>> {
        return repository.loadCitiesByCityName(
            cityName = params?.city ?: ""
        ).map {
            it
        }
    }

    class SearchCitiesParams(
        val city: String = ""
    ) : Params()
}
