package com.doapps.android.weatherapp.ui.dashboard

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.doapps.android.weatherapp.core.BaseViewModel
import com.doapps.android.conexionmodule.db.room.entity.CurrentWeatherEntity
import com.doapps.android.conexionmodule.db.room.entity.ForecastEntity
import com.doapps.android.domain.usecase.CurrentWeatherUseCase
import com.doapps.android.domain.usecase.ForecastUseCase
import com.doapps.android.domain.utils.status.Resource


class DashboardFragmentViewModel (private val forecastUseCase: ForecastUseCase, private val currentWeatherUseCase: CurrentWeatherUseCase, var sharedPreferences: SharedPreferences) : BaseViewModel() {

    private val _forecastParams: MutableLiveData<ForecastUseCase.ForecastParams> = MutableLiveData()
    private val _currentWeatherParams: MutableLiveData<CurrentWeatherUseCase.CurrentWeatherParams> = MutableLiveData()

    fun getForecastViewState() = forecastViewState
    fun getCurrentWeatherViewState() = currentWeatherViewState

    private val forecastViewState: LiveData<Resource<ForecastEntity>> = _forecastParams.switchMap { params ->
        forecastUseCase.execute(params)
    }
    private val currentWeatherViewState: LiveData<Resource<CurrentWeatherEntity>> = _currentWeatherParams.switchMap { params ->
        currentWeatherUseCase.execute(params)
    }

    fun setForecastParams(params: ForecastUseCase.ForecastParams) {
        if (_forecastParams.value == params)
            return
        _forecastParams.postValue(params)
    }

    fun setCurrentWeatherParams(params: CurrentWeatherUseCase.CurrentWeatherParams) {
        if (_currentWeatherParams.value == params)
            return
        _currentWeatherParams.postValue(params)
    }
}
