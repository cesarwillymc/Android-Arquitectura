package com.doapps.android.weatherapp.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(

    @SerializedName( "country")
    val country: String?,

    @SerializedName( "coord")
    val coord: Coord?,

    @SerializedName( "name")
    val name: String?,

    @SerializedName( "id")
    val id: Int?
) : Parcelable
