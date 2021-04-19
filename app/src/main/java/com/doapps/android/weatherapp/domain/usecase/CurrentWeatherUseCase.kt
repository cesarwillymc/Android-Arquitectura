package com.doapps.android.weatherapp.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.doapps.android.weatherapp.core.Constants
import com.doapps.android.weatherapp.db.entity.CurrentWeatherEntity
import com.doapps.android.weatherapp.repo.CurrentWeatherRepository
import com.doapps.android.weatherapp.utils.UseCaseLiveData
import com.doapps.android.weatherapp.utils.domain.Resource



class CurrentWeatherUseCase (private val repository: CurrentWeatherRepository) : UseCaseLiveData<Resource<CurrentWeatherEntity>, CurrentWeatherUseCase.CurrentWeatherParams, CurrentWeatherRepository>() {


    override fun buildUseCaseObservable(params: CurrentWeatherParams?): LiveData<Resource<CurrentWeatherEntity>> {
        return repository.loadCurrentWeatherByGeoCords(
            params?.lat?.toDouble() ?: 0.0,
            params?.lon?.toDouble() ?: 0.0,
            params?.fetchRequired
                ?: false,
            units = params?.units ?: Constants.Coords.METRIC
        ).map {
            it
        }
    }

    class CurrentWeatherParams(
        val lat: String = "",
        val lon: String = "",
        val fetchRequired: Boolean,
        val units: String
    ) : Params()
}
