package com.doapps.android.conexionmodule.network.model

import com.google.gson.annotations.SerializedName

data class Geoloc(

    @SerializedName("lng")
    val lng: Double? = null,

    @SerializedName( "lat")
    val lat: Double? = null
)
