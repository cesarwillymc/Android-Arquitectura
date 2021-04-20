package com.doapps.android.conexionmodule.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wind(

    @SerializedName( "deg")
    val deg: Double?,

    @SerializedName( "speed")
    val speed: Double?
) : Parcelable
