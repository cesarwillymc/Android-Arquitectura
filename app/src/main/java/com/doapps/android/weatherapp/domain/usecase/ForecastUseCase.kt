package com.doapps.android.weatherapp.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.doapps.android.weatherapp.core.Constants
import com.doapps.android.conexionmodule.db.room.entity.ForecastEntity
import com.doapps.android.weatherapp.repo.forecast.ForecastRepository
import com.doapps.android.weatherapp.repo.forecast.ForecastRepositoryImpl
import com.doapps.android.weatherapp.ui.dashboard.ForecastMapper
import com.doapps.android.weatherapp.utils.UseCaseLiveData
import com.doapps.android.weatherapp.utils.domain.Resource





class ForecastUseCase (private val repository: ForecastRepository) : UseCaseLiveData<Resource<ForecastEntity>, ForecastUseCase.ForecastParams, ForecastRepositoryImpl>() {


    override fun buildUseCaseObservable(params: ForecastParams?): LiveData<Resource<ForecastEntity>> {
        return repository.loadForecastByCoord(
            params?.lat?.toDouble() ?: 0.0,
            params?.lon?.toDouble() ?: 0.0,
            params?.fetchRequired
                ?: false,
            units = params?.units ?: Constants.Coords.METRIC
        ).map {
            onForecastResultReady(it)
        }
    }

    private fun onForecastResultReady(resource: Resource<ForecastEntity>): Resource<ForecastEntity> {
        val mappedList = resource.data?.list?.let { ForecastMapper().mapFrom(it) }
        resource.data?.list = mappedList
        return resource
    }

    class ForecastParams(
        val lat: String = "",
        val lon: String = "",
        val fetchRequired: Boolean,
        val units: String
    ) : Params()
}
