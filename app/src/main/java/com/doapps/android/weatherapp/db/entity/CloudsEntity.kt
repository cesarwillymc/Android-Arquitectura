package com.doapps.android.weatherapp.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import com.doapps.android.weatherapp.domain.model.Clouds
import kotlinx.android.parcel.Parcelize



@Parcelize
@Entity(tableName = "Clouds")
data class CloudsEntity(
    @ColumnInfo(name = "all")
    var all: Int
) : Parcelable {
    @Ignore
    constructor(clouds: Clouds?) : this(
        all = clouds?.all ?: 0
    )
}
