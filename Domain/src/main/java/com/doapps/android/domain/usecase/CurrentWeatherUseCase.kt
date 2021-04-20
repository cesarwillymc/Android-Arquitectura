package com.doapps.android.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.doapps.android.conexionmodule.db.room.entity.CurrentWeatherEntity
import com.doapps.android.domain.repo.currentweather.CurrentWeatherRepository
import com.doapps.android.domain.repo.currentweather.CurrentWeatherRepositoryImpl
import com.doapps.android.domain.utils.Constants
import com.doapps.android.domain.utils.status.Resource
import com.doapps.android.domain.utils.UseCaseLiveData


class CurrentWeatherUseCase(private val repository: CurrentWeatherRepository) :
    UseCaseLiveData<Resource<CurrentWeatherEntity>, CurrentWeatherUseCase.CurrentWeatherParams, CurrentWeatherRepositoryImpl>() {


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
