package com.doapps.android.conexionmodule.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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
