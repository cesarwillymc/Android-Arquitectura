package com.doapps.android.weatherapp.ui.weather_detail.weatherHourOfDay

import androidx.databinding.ObservableField
import com.doapps.android.conexionmodule.network.model.ListItem
import com.doapps.android.weatherapp.core.BaseViewModel



class WeatherHourOfDayItemViewModel  : BaseViewModel() {
    var item = ObservableField<ListItem>()
}
