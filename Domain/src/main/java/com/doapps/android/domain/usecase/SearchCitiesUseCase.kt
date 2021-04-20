package com.doapps.android.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.doapps.android.conexionmodule.db.room.entity.CitiesForSearchEntity
import com.doapps.android.domain.repo.searchcities.SearchCitiesRepository
import com.doapps.android.domain.repo.searchcities.SearchCitiesRepositoryImpl
import com.doapps.android.domain.utils.status.Resource
import com.doapps.android.domain.utils.UseCaseLiveData


class SearchCitiesUseCase(private val repository: SearchCitiesRepository) :
    UseCaseLiveData<Resource<List<CitiesForSearchEntity>>, SearchCitiesUseCase.SearchCitiesParams, SearchCitiesRepositoryImpl>() {

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
