package com.doapps.android.weatherapp.db.entity

import android.os.Parcelable
import androidx.room.*
import com.doapps.android.weatherapp.domain.model.ForecastResponse
import com.doapps.android.weatherapp.domain.model.ListItem
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "Forecast")
data class ForecastEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @Embedded
    var city: CityEntity?,

    @ColumnInfo(name = "list")
    var list: List<ListItem>?
) : Parcelable {

    @Ignore
    constructor(forecastResponse: ForecastResponse) : this(
        id = 0,
        city = forecastResponse.city?.let { CityEntity(it) },
        list = forecastResponse.list
    )
}
