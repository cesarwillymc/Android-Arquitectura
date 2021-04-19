package com.doapps.android.weatherapp.ui.dashboard.forecast

import androidx.databinding.ObservableField
import com.doapps.android.weatherapp.core.BaseViewModel
import com.doapps.android.weatherapp.domain.model.ListItem



class ForecastItemViewModel  : BaseViewModel() {
    var item = ObservableField<ListItem>()
}
