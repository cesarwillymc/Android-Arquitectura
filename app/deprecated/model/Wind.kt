package com.doapps.android.weatherapp.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Wind(

    @SerializedName( "deg")
    val deg: Double?,

    @SerializedName( "speed")
    val speed: Double?
) : Parcelable
