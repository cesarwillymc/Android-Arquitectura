package com.doapps.android.conexionmodule.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Snow(

    @SerializedName( "3h")
    val jsonMember3h: Double?
) : Parcelable
