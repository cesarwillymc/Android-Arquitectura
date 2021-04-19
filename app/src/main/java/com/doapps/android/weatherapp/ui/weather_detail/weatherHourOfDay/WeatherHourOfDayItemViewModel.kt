package com.doapps.android.weatherapp.ui.weather_detail.weatherHourOfDay

import androidx.databinding.ObservableField
import com.doapps.android.weatherapp.core.BaseViewModel
import com.doapps.android.weatherapp.domain.model.ListItem



class WeatherHourOfDayItemViewModel  : BaseViewModel() {
    var item = ObservableField<ListItem>()
}
