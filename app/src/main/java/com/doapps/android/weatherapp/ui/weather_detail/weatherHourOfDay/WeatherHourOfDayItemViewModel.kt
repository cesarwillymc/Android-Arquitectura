package com.doapps.android.weatherapp.ui.weather_detail.weatherHourOfDay

import androidx.databinding.ObservableField
import com.doapps.android.weatherapp.core.BaseViewModel
import com.doapps.android.weatherapp.domain.model.ListItem

/**
 * Created by Furkan on 2019-10-26
 */

class WeatherHourOfDayItemViewModel  : BaseViewModel() {
    var item = ObservableField<ListItem>()
}
