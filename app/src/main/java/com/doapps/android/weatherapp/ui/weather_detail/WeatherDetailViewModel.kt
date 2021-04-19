package com.doapps.android.weatherapp.ui.weather_detail

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doapps.android.weatherapp.core.BaseViewModel
import com.doapps.android.weatherapp.db.entity.ForecastEntity
import com.doapps.android.weatherapp.domain.datasource.forecast.ForecastLocalDataSource
import com.doapps.android.weatherapp.domain.model.ListItem

class WeatherDetailViewModel (private val forecastLocalDataSource: ForecastLocalDataSource) : BaseViewModel() {

    var weatherItem: ObservableField<ListItem> = ObservableField()
    private var forecastLiveData: LiveData<ForecastEntity> = MutableLiveData()
    var selectedDayDate: String? = null
    var selectedDayForecastLiveData: MutableLiveData<List<ListItem>> = MutableLiveData()

    fun getForecastLiveData() = forecastLiveData

    fun getForecast(): LiveData<ForecastEntity> {
        return forecastLocalDataSource.getForecast()
    }
}
