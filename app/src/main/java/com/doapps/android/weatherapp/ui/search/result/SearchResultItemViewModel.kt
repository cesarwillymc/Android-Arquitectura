package com.doapps.android.weatherapp.ui.search.result

import androidx.databinding.ObservableField
import com.doapps.android.weatherapp.core.BaseViewModel
import com.doapps.android.conexionmodule.db.room.entity.CitiesForSearchEntity




class SearchResultItemViewModel : BaseViewModel() {
    var item = ObservableField<CitiesForSearchEntity>()
}
