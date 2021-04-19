package com.doapps.android.weatherapp.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForecastResponse(

    @SerializedName( "city")
    val city: City?,

    @SerializedName( "cnt")
    val cnt: Int?,

    @SerializedName( "cod")
    val cod: String?,

    @SerializedName( "message")
    val message: Double?,

    @SerializedName( "list")
    val list: List<ListItem>?
) : Parcelable
