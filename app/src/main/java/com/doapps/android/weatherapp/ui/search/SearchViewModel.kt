package com.doapps.android.weatherapp.ui.search

import android.content.SharedPreferences
import androidx.lifecycle.*
import com.doapps.android.weatherapp.core.BaseViewModel
import com.doapps.android.weatherapp.core.Constants
import com.doapps.android.conexionmodule.db.room.entity.CitiesForSearchEntity
import com.doapps.android.conexionmodule.db.room.entity.CoordEntity
import com.doapps.android.weatherapp.domain.usecase.SearchCitiesUseCase
import com.doapps.android.weatherapp.utils.domain.Resource
import kotlinx.coroutines.*


class SearchViewModel (private val useCase: SearchCitiesUseCase, private val pref: SharedPreferences) : BaseViewModel() {

    private val _searchParams: MutableLiveData<SearchCitiesUseCase.SearchCitiesParams> = MutableLiveData()
    fun getSearchViewState() = searchViewState

    private val searchViewState: LiveData<Resource<List<CitiesForSearchEntity>>> = _searchParams.switchMap { params ->
        useCase.execute(params)
    }

    fun setSearchParams(params: SearchCitiesUseCase.SearchCitiesParams) {
        if (_searchParams.value == params)
            return
        _searchParams.postValue(params)
    }

    fun saveCoordsToSharedPref(coordEntity: CoordEntity) = liveData<Boolean> {
        emit(false)
        GlobalScope.async {
            pref.edit().putString(Constants.Coords.LAT, coordEntity.lat.toString()).apply()
            pref.edit().putString(Constants.Coords.LON, coordEntity.lon.toString()).apply()
        }.await()
        emit(true)
    }
}
