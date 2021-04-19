package com.doapps.android.weatherapp.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Snow(

    @SerializedName( "3h")
    val jsonMember3h: Double?
) : Parcelable
