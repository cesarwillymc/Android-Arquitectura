package com.doapps.android.weatherapp.ui.dashboard.forecast

import androidx.databinding.ObservableField
import com.doapps.android.conexionmodule.network.model.ListItem
import com.doapps.android.weatherapp.core.BaseViewModel



class ForecastItemViewModel  : BaseViewModel() {
    var item = ObservableField<ListItem>()
}
